package com.richgo.file.springmvc;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.DownloadStream;
import org.csource.fastdfs.ProtoCommon;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.csource.fastdfs.UploadStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import com.richgo.common.CodeConts;
import com.richgo.file.ResultString;

/**
 * 
 * springmvc文件上传下载工具类
 * 
 * @author zhouxy
 *
 */
public class FastDFSUtil {
	private static final Logger log = LoggerFactory.getLogger(FastDFSUtil.class);

	static {
		String fdfsClientConfigFilePath = null;
		try {
			// 加载配置文件,初始化全局配置。
			String classPath = new File(FastDFSUtil.class.getResource("/").getFile()).getCanonicalPath();

			fdfsClientConfigFilePath = classPath + File.separator + "fastdfs-Client.conf";

			ClientGlobal.init(fdfsClientConfigFilePath);
		} catch (Exception ex) {
			log.error("获取配置文件初始化异常,配置文件路径为{}", fdfsClientConfigFilePath, ex);
		}
	}

	/**
	 * Description:远程选择上传文件-通过MultipartFile
	 *
	 * @param file
	 *            MultipartFile 文件流
	 * @param groupName
	 *            String 组名称
	 * @return Map<String,Object> code-返回代码, group-文件组, msg-文件路径/错误信息
	 */
	public static ResultString upload(MultipartFile file, String groupName) {
		// tracker服务器
		TrackerServer trackerServer = null;
		// Storage服务器
		StorageServer storageServer = null;
		// 文件名称
		String tempFileName = null;

		InputStream fis = null;

		ResultString retStr = new ResultString();
		try {
			if (file == null || file.isEmpty()) {
				retStr.setCode(CodeConts.PARAM_LEGAL);
				log.error("上传文件file为空");
				return retStr;
			}
			if (StringUtils.isBlank(groupName)) {
				groupName = null;
			}

			// 获取文件名称
			tempFileName = file.getOriginalFilename();

			// 截取后缀
			String fileExtName = null;
			int nPos = tempFileName.lastIndexOf('.');
			if (nPos > 0 && tempFileName.length() - nPos <= ProtoCommon.FDFS_FILE_EXT_NAME_MAX_LEN + 1) {
				fileExtName = tempFileName.substring(nPos + 1);
			}

			// 设置元信息
			NameValuePair[] metaList = new NameValuePair[3];
			// 原始文件名称
			metaList[0] = new NameValuePair("fileName", tempFileName);
			metaList[1] = new NameValuePair("fileExtName", fileExtName);
			// 文件大小
			long fileSize = file.getSize();
			metaList[2] = new NameValuePair("fileLength", String.valueOf(fileSize));

			// 创建tracker连接
			trackerServer = getTrackerServer();
			// 获取StorageClient
			StorageClient storageClient = getStorageClient(trackerServer);

			fis = file.getInputStream();
			// 上传文件,返回值{group名称，相对路径}
			String[] retArray = storageClient.upload_file(groupName, fileSize, new UploadStream(fis, fileSize),
					fileExtName, metaList);

			if (retArray == null || retArray.length != 2) {
				retStr.setCode(CodeConts.RESULT_FAIURE);
				log.error("返回内容为null或长度不为2");
			}
			// 用户上传文件名称、group名称、文件在fastdfs相对路径
			retStr.setCode(CodeConts.SUCCESS);
			retStr.setOriginalFilename(tempFileName);
			retStr.setFileExtName(fileExtName);
			retStr.setGroupName(retArray[0]);
			retStr.setFastdfsPath(retArray[1]);

		} catch (Exception e) {
			retStr.setCode(CodeConts.SYS_ERR);
			log.error("上传异常,文件名称{}", tempFileName, e);
		} finally {
			// 关闭跟踪服务器的连接
			colse(storageServer, trackerServer);
		}
		return retStr;
	}

	/**
	 * 下载文件
	 * 
	 * M00/00/00/rBCjM1kWzbaAGUOtMh9LNkNr7P4446.jpg
	 *
	 * @param response
	 *            HttpServletResponse
	 * @param group
	 *            String group名称
	 * @param filepath
	 *            String 数据库存的文件路径
	 * @param downName
	 *            String 下载后的文件名称
	 * @throws IOException
	 */
	public static ResultString download(HttpServletResponse response, String groupName, String filepath,
			String downName) {
		if (log.isInfoEnabled()) {
			log.info("参数：response=" + response + "，groupName=" + groupName + "，filepath=" + filepath + "，downName="
					+ downName);
		}

		ResultString retStr = new ResultString();
		if (response == null || StringUtils.isBlank(groupName) || StringUtils.isBlank(filepath)
				|| StringUtils.isBlank(downName)) {
			retStr.setCode(CodeConts.PARAM_LEGAL);
			log.error("SpringMVCUploadFileUtil类download方法：参数有为空response=" + filepath + "，groupName=" + groupName
					+ "，filepath=" + filepath);
			return retStr;
		}

		// tracker服务器
		TrackerServer trackerServer = null;
		// Storage服务器
		StorageServer storageServer = null;

		try {
			if (StringUtils.isBlank(groupName)) {
				groupName = null;
			}

			// 创建tracker连接
			trackerServer = getTrackerServer();
			// 获取StorageClient
			StorageClient storageClient = getStorageClient(trackerServer);

			OutputStream outputStream = response.getOutputStream();
			// 设置下载后文件名称
			response.setHeader("Content-disposition", "attachment;filename=" + downName);
			if (log.isInfoEnabled()) {
				log.info("下载文件..");
			}
			// 调用客户端的下载download_file的方法
			int ret = storageClient.download_file(groupName, filepath, new DownloadStream(outputStream));
			if (ret == 0) {// 0 success, return none zero errno if fail
				retStr.setCode(CodeConts.SUCCESS);
			} else {
				retStr.setCode(CodeConts.RESULT_FAIURE);
				log.error("下载文件返回值为空");
			}
		} catch (Exception e) {
			retStr.setCode(CodeConts.SYS_ERR);
			log.error("下载异常", e);
		} finally {
			// 关闭跟踪服务器的连接
			colse(storageServer, trackerServer);
		}

		return retStr;
	}

	/**
	 * 
	 * 创建TrackerServer对象
	 * 
	 * @return TrackerServer
	 * @throws IOException
	 */
	private static TrackerServer getTrackerServer() throws IOException {
		// 创建一个TrackerClient对象。
		TrackerClient trackerClient = new TrackerClient();
		// 创建一个TrackerServer对象。
		return trackerClient.getConnection();
	}

	/**
	 * 
	 * 获取storage客户端对象
	 * 
	 * @param trackerServer
	 *            TrackerServer
	 * @return StorageClient
	 */
	private static StorageClient getStorageClient(TrackerServer trackerServer) {
		// 获得StorageClient对象。
		return new StorageClient(trackerServer, null);
	}

	/**
	 * 关闭跟踪服务器的连接
	 *
	 * @param storageServer
	 *            StorageServer
	 * @param trackerServer
	 *            TrackerServer
	 */
	private static void colse(StorageServer storageServer, TrackerServer trackerServer) {
		if (storageServer != null) {
			try {
				storageServer.close();
			} catch (IOException e) {
				log.error("关闭storageServer异常", e);
			}
		}
		if (trackerServer != null) {
			try {
				trackerServer.close();
			} catch (IOException e) {
				log.error("关闭trackerServer异常", e);
			}
		}
	}
}
