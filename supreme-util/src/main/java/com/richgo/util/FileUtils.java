package com.richgo.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 文件操作工具类
 * 
 * @author mcl Nov 13, 2012 11:34:55 AM
 */
public class FileUtils extends org.apache.commons.io.FileUtils {
	/* 日志对象 */
	private final static Logger logger = LoggerFactory.getLogger(FileUtils.class);

	public static final int RET_SUCCESS = 1;
	public static final int RET_FAIL = -1;
	public static final int RET_NOT_EXISTS = 0;
	public static final int RET_INIT = -2;

	public static final int FILETYPE_ALL = 0;
	public static final int FILETYPE_FILE = 1;
	public static final int FILETYPE_DIRECTORY = 2;

	public static String tokens = "/";

	/**
	 * 
	 * 获取classpath下给定名称对应的文件路径
	 * 
	 * @param name
	 *            String
	 * @return String
	 */
	public static String getClassPathName(String paramName) {

		String classpath = FileUtils.getClassPath();

		String name = paramName;

		if (StringUtils.isBlank(name)) {
			if (classpath.endsWith("/")) {
				return classpath;
			} else {
				return Tool.concat(classpath, "/");
			}
		} else {
			if (classpath.endsWith("/")) {
				if (name.startsWith("/")) {
					name = name.substring(1);
				}
				return Tool.concat(classpath, name);
			} else {
				if (name.startsWith("/")) {
					return Tool.concat(classpath, name);
				} else {
					return Tool.concat(classpath, "/", name);
				}
			}
		}
	}

	/**
	 * 
	 * 获取classpath路径
	 * 
	 * @return String
	 */
	public static String getClassPath() {
		return FileUtils.class.getResource("/").getPath();
	}

	/**
	 * 创建磁盘路径，如果已经存在则不创建
	 * 
	 * @param url
	 */
	public static void creatDirectory(String url) {
		StringTokenizer st = new StringTokenizer(url, "/");
		StringBuffer sBuffer = new StringBuffer();
		sBuffer.append(st.nextToken());
		sBuffer.append(tokens);
		while (st.hasMoreTokens()) {
			sBuffer.append(st.nextToken());
			sBuffer.append(tokens);
			File inbox = new File(sBuffer.toString());
			if (!inbox.exists()) {
				inbox.mkdir();
			}
		}
	}

	/**
	 * 检查文件或目录是否存在
	 * 
	 * @param filepathname
	 * @return
	 */
	public static boolean isExsit(String filepathname) {
		boolean returnValue = false;
		File file = null;

		try {
			if (filepathname == null)
				return false;

			file = new File(filepathname);

			returnValue = file.exists();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return returnValue;
	}

	/**
	 * 创建目录
	 * 
	 * @param directoryPathName
	 * @return
	 */
	public static int createDirectory(String directoryPathName) {
		File file = null;
		int returnValue = RET_FAIL;
		try {
			file = new File(directoryPathName);

			if (file.exists())
				return RET_SUCCESS;

			if (file.mkdirs())
				returnValue = RET_SUCCESS;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			returnValue = RET_FAIL;
		} finally {
			file = null;
		}

		return returnValue;
	}

	/**
	 * 创建目录
	 * 
	 * @param directoryPath
	 * @param directoryName
	 * @return
	 */
	public static int createDirectory(String directoryPath, String directoryName) {

		return createDirectory(directoryPath + "/" + directoryName);
	}

	/**
	 * 递归删除目录和所包含文件
	 * 
	 * @param file
	 * @return
	 */
	public static int deleteFileAndDirectory(File file) {
		int returnValue = RET_INIT;
		File[] fileList = null;

		try {
			if (!file.exists()) {
				return RET_NOT_EXISTS;
			}

			if (file.isDirectory()) {
				fileList = file.listFiles();

				for (int i = 0; i < fileList.length; i++) {
					if (deleteFileAndDirectory(fileList[i]) == RET_FAIL)
						return RET_FAIL;
				}
			}

			if (file.delete())
				returnValue = RET_SUCCESS;
			else
				returnValue = RET_FAIL;

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			file = null;
		}
		return returnValue;
	}

	/**
	 * 递归删除目录和所包含文件
	 * 
	 * @param directoryPathName
	 * @return
	 */
	public static int deleteDirectory(String directoryPathName) {
		return deleteFileAndDirectory(new File(directoryPathName));
	}

	/**
	 * 递归删除目录和所包含文件
	 * 
	 * @param directoryPath
	 * @param directoryName
	 * @return
	 */
	public static int deleteDirectory(String directoryPath, String directoryName) {
		return deleteFileAndDirectory(new File(directoryPath + "/" + directoryName));
	}

	/**
	 * 删除文件
	 * 
	 * @param filePathName
	 * @return
	 */
	public static int deleteFile(String filePathName) {
		return deleteFileAndDirectory(new File(filePathName));
	}

	/**
	 * 删除文件
	 * 
	 * @param filePath
	 * @param fileName
	 * @return
	 */
	public static int deleteFile(String filePath, String fileName) {
		return deleteFileAndDirectory(new File(filePath + "/" + fileName));
	}

	/**
	 * 移动文件并改名
	 * 
	 * @param oldFileAbsolutePath
	 * @param newFileAbsolutePath
	 * @return
	 */
	public static int moveFile(String oldFileAbsolutePath, String newFileAbsolutePath) {

		if (oldFileAbsolutePath == null || newFileAbsolutePath == null) {
			logger.error("moveFile(String oldFileAbsolutePath, String newFileAbsolutePath) method parameter is null");
			return RET_FAIL;
		}
		return moveFileByFile(new File(oldFileAbsolutePath), new File(newFileAbsolutePath));

	}

	/**
	 * 移动文件并改名
	 * 
	 * @param oldFile
	 * @param newFile
	 * @return
	 */
	public static int moveFileByFile(File oldFile, File newFile) {
		int returnValue = RET_FAIL;

		try {
			if (oldFile == null || newFile == null)
				return RET_FAIL;

			// 判断是否被移动文件不存在
			if (!oldFile.exists())
				return RET_NOT_EXISTS;

			// 新目录是否存在，如果不存在就创建
			if (!newFile.getParentFile().exists())
				newFile.getParentFile().mkdirs();

			// 新文件是否存在，如果存在就删除
			if (newFile.exists())
				newFile.delete();

			// 重命名
			if (oldFile.renameTo(newFile)) {
				if (oldFile.exists())
					oldFile.delete();
			}

			returnValue = RET_SUCCESS;

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return returnValue;

	}

	/**
	 * 是否是有效的扩展名(多个扩展名)
	 * 
	 * @param filename
	 * @param extNameArray
	 * @return
	 */
	public static boolean isVaildExtNames(String filename, String[] extNameArray) {
		boolean returnValue = false;

		if (filename == null)
			return false;

		// 未指定扩展名，人为全部扩展名均可
		if (extNameArray == null || extNameArray.length == 0)
			return true;

		//
		for (int i = 0; i < extNameArray.length; i++) {
			if (isVaildExtName(filename, extNameArray[i]))
				return true;
		}

		return returnValue;
	}

	/**
	 * 是否是有效的扩展名
	 * 
	 * @param filename
	 * @param extName
	 * @return
	 */
	public static boolean isVaildExtName(String filename, String extName) {
		boolean returnValue = false;
		String fileNameString = null;
		String extNameString = null;
		int index = -1;
		String extNameTemp = null;

		try {
			if (filename == null)
				return false;

			// 未指定扩展名，人为全部扩展名均可
			if (extName == null)
				return true;

			// 确定是否为无扩展名情况
			if (!extName.equals("")) {
				// 扩展名开始必须带.
				if (extName.indexOf(".") != 0)
					extName = "." + extName;

				fileNameString = filename.toUpperCase();
				extNameString = extName.toUpperCase();

				index = fileNameString.lastIndexOf(extNameString);

				if (index > -1) {
					extNameTemp = fileNameString.substring(index, fileNameString.length());

					if (extNameTemp.equals(extNameString))
						returnValue = true;
				}
			} else {
				if (filename.indexOf(".") == -1)
					returnValue = true;
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return returnValue;

	}

	/**
	 * 修改文件的扩展名
	 * 
	 * @param filepathname
	 * @param oldExtName
	 * @param newExtName
	 * @param isRealFile
	 * @return
	 */
	public static String modifyExtFileName(String filepathname, String oldExtName, String newExtName,
			boolean isRealFile) {
		String returnValue = null;
		File file = null;
		File newFile = null;

		try {
			if (filepathname == null || oldExtName == null || newExtName == null)
				return null;

			if (isRealFile) {
				file = new File(filepathname);

				if (!file.exists())
					return null;
			}

			if (newExtName.indexOf(".") != 0 && !newExtName.equals(""))
				newExtName = "." + newExtName;

			if (oldExtName.equals("")) {

				if (filepathname.lastIndexOf(".") == -1)
					returnValue = filepathname + newExtName;
				else
					returnValue = filepathname.substring(0, filepathname.lastIndexOf(".")) + newExtName;
			} else {

				if (oldExtName.indexOf(".") != 0)
					oldExtName = "." + oldExtName;

				if (!isVaildExtName(filepathname, oldExtName))
					return null;

				returnValue = filepathname.substring(0, filepathname.lastIndexOf(oldExtName)) + newExtName;
			}

			if (isRealFile) {
				newFile = new File(returnValue);
				if (newFile.exists())
					newFile.delete();
				if (!file.renameTo(newFile))
					returnValue = null;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return returnValue;

	}

	/**
	 * 文件名中是否包含指定字符串(多个字符串)
	 * 
	 * @param filename
	 * @param containStringArray
	 * @return
	 */
	public static boolean isContainStrings(String filename, String[] containStringArray) {
		boolean returnValue = false;

		if (filename == null)
			return false;

		// 未指定字符串，人为全部字符串均可
		if (containStringArray == null || containStringArray.length == 0)
			return true;

		for (int i = 0; i < containStringArray.length; i++) {
			if (isContainString(filename, containStringArray[i]))
				return true;
		}

		return returnValue;
	}

	/**
	 * 文件名中是否包含指定字符串
	 * 
	 * @param filename
	 * @param containString
	 * @return
	 */
	public static boolean isContainString(String filename, String containString) {
		boolean returnValue = false;

		if (filename == null)
			return false;

		// 未指定字符串，人为全部字符串均可
		if (containString == null)
			return true;

		filename = filename.toUpperCase();
		containString = containString.toUpperCase();

		if (filename.indexOf(containString) > -1)
			returnValue = true;

		return returnValue;
	}

	/**
	 * 获取目录下的文件路径列表
	 * 
	 * @param directoryPath
	 *            目录
	 * @param returnType
	 *            文件类型，0：全部 1：文件 2：目录
	 * @param containNameStringArray
	 *            文件名中所包含的字符串
	 * @param extNameArray
	 *            扩展名
	 * @return
	 */
	public static List<String> getList(String directoryPath, int returnType, String[] containNameStringArray,
			String[] extNameArray) {
		List<String> returnValue = null;
		File file = null;
		File[] fileList = null;

		try {
			file = new File(directoryPath);

			if (!file.exists()) {

				return null;
			}

			fileList = file.listFiles();

			if (fileList == null || fileList.length == 0)
				return null;

			returnValue = new ArrayList<String>();

			for (int i = 0; i < fileList.length; i++) {

				if (returnType == FILETYPE_DIRECTORY && fileList[i].isDirectory()) {
					if (isContainStrings(fileList[i].getName(), containNameStringArray)) {

						returnValue.add(fileList[i].getAbsolutePath());
					}

				} else if (returnType == FILETYPE_FILE && fileList[i].isFile()) {

					if (isContainStrings(fileList[i].getName(), containNameStringArray)
							&& isVaildExtNames(fileList[i].getName(), extNameArray)) {

						returnValue.add(fileList[i].getAbsolutePath());
					}
				} else if (returnType == FILETYPE_ALL) {

					if (isContainStrings(fileList[i].getName(), containNameStringArray))
						returnValue.add(fileList[i].getAbsolutePath());
				}

			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return returnValue;
	}

	/**
	 * 根据条件获取目录下的文件路径
	 * 
	 * @param directoryPath
	 *            目录
	 * @param returnType
	 *            文件类型，0：全部 1：文件 2：目录
	 * @param containNameString
	 *            文件名中所包含的字符串
	 * @param extName
	 *            扩展名
	 * @return 返回符合条件的文件名，包括路径。没有找到则返回null
	 */
	public static String getFilePathName(String directoryPath, int returnType, String containNameString,
			String extName) {
		String returnValue = null;
		File file = null;
		File[] fileList = null;

		try {
			file = new File(directoryPath);

			if (!file.exists()) {
				logger.error("directoryPath not exists:" + directoryPath);
				return null;
			}

			fileList = file.listFiles();

			if (fileList == null || fileList.length == 0) {
				return null;
			}

			for (int i = 0; i < fileList.length; i++) {

				if (returnType == FILETYPE_DIRECTORY && fileList[i].isDirectory()) {
					if (isContainString(fileList[i].getName(), containNameString)) {

						returnValue = fileList[i].getAbsolutePath();
					}

				} else if (returnType == FILETYPE_FILE && fileList[i].isFile()) {

					if (isContainString(fileList[i].getName(), containNameString)
							&& isVaildExtName(fileList[i].getName(), extName)) {

						returnValue = fileList[i].getAbsolutePath();
					}
				} else if (returnType == FILETYPE_ALL) {

					if (isContainString(fileList[i].getName(), containNameString))
						returnValue = fileList[i].getAbsolutePath();
				}

			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return returnValue;
	}

	/**
	 * 根据条件获取目录下的文件路径列表
	 * 
	 * @param directoryPath
	 *            目录
	 * @param returnType
	 *            文件类型，0：全部 1：文件 2：目录
	 * @param containNameString
	 *            文件名中所包含的字符串
	 * @param extName
	 *            扩展名
	 * @return 返回符合条件的文件名，包括路径。没有找到则返回null
	 */
	public static List<String> getFilePathNameList(String directoryPath, int returnType, String containNameString,
			String extName) {
		List<String> resultList = new ArrayList<String>();
		File file = null;
		File[] fileList = null;

		try {
			if (directoryPath != null) {
				file = new File(directoryPath);
				if (file.exists()) {
					fileList = file.listFiles();
					if (fileList != null && fileList.length != 0) {
						for (int i = 0; i < fileList.length; i++) {
							if (returnType == FILETYPE_DIRECTORY && fileList[i].isDirectory()) {
								if (isContainString(fileList[i].getName(), containNameString)) {
									resultList.add(fileList[i].getAbsolutePath());
								}
							} else if (returnType == FILETYPE_FILE && fileList[i].isFile()) {
								if (isContainString(fileList[i].getName(), containNameString)
										&& isVaildExtName(fileList[i].getName(), extName)) {
									resultList.add(fileList[i].getAbsolutePath());
								}
							} else if (returnType == FILETYPE_ALL) {
								if (isContainString(fileList[i].getName(), containNameString)) {
									resultList.add(fileList[i].getAbsolutePath());
								}
							}
						}
					}
				} else {
					logger.error("Directory does not exist:" + directoryPath);
				}
			} else {
				logger.error("parameter 'directoryPath' is null");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return resultList;
	}

	/**
	 * 读文件
	 * 
	 * @param filename
	 * @return
	 */
	public static List<String> readFile(String filename) {
		List<String> returnValue = null;
		File file = null;
		file = new File(filename);
		returnValue = readFile(file);
		return returnValue;
	}

	/**
	 * 读文件
	 * 
	 * @param file
	 * @return
	 */
	public static List<String> readFile(File file) {
		List<String> returnValue = null;
		BufferedReader bufferedReader = null;
		String line = null;

		try {
			if (!file.exists())
				return null;

			bufferedReader = new BufferedReader(new FileReader(file));
			returnValue = new ArrayList<String>();
			while ((line = bufferedReader.readLine()) != null) {
				returnValue.add(line);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			try {
				if (bufferedReader != null)
					bufferedReader.close();
				bufferedReader = null;
				line = null;
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
		}

		return returnValue;
	}

	/**
	 * 读文件
	 * 
	 * @param file
	 *            File
	 * @return String
	 */
	public static String readFileDefaultStr(File file) {

		StringBuffer buff = new StringBuffer();

		BufferedReader bufferedReader = null;

		String line = null;
		try {
			if (!file.exists())
				return null;

			bufferedReader = new BufferedReader(new FileReader(file));
			while ((line = bufferedReader.readLine()) != null) {
				buff.append(line);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			try {
				if (bufferedReader != null)
					bufferedReader.close();
				bufferedReader = null;
				line = null;
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
		}

		return buff.toString();
	}

	/**
	 * 读文件
	 * 
	 * @param file
	 *            File
	 * @return String
	 */
	public static String readFileStr(File file) {

		StringBuffer buff = new StringBuffer();

		BufferedReader bufferedReader = null;
		InputStreamReader isr = null;
		FileInputStream fis = null;
		String line = null;
		try {
			if (!file.exists())
				return null;

			fis = new FileInputStream(file);
			isr = new InputStreamReader(fis, "UTF-8");
			bufferedReader = new BufferedReader(isr);

			while ((line = bufferedReader.readLine()) != null) {
				buff.append(line);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			try {
				if (bufferedReader != null)
					bufferedReader.close();
				bufferedReader = null;
				line = null;
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}

			if (isr != null) {
				try {
					isr.close();
				} catch (IOException e) {
					logger.error(e.getMessage(), e);
				}
			}

			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					logger.error(e.getMessage(), e);
				}
			}

		}

		return buff.toString();
	}

	/**
	 * 
	 * 获取给定的property文件内容
	 * 
	 * 返回为null时读取文件失败
	 * 
	 * @param path
	 *            String 文件路径
	 * @return Properties Properties对象或NULL
	 */
	public static Properties readProperties(String path) {
		if (StringUtils.isEmpty(path)) {
			logger.error("文件路径为空");
			return null;
		}

		// 属性对象
		Properties properties = new Properties();
		FileInputStream inputStream = null;

		try {
			// 读取文件
			inputStream = new FileInputStream(path);
			properties.load(inputStream);
		} catch (Exception e) {
			logger.error("文件路径{}", path, e);
			return null;
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					logger.error("文件路径{}", path, e);
					return null;
				}
			}
		}
		return properties;
	}

	/**
	 * 写文件
	 * 
	 * @param file
	 * @param content
	 * @param append
	 * @return
	 */
	public static boolean writeFile(File file, String content, boolean append) {
		boolean returnValue = false;
		BufferedWriter bufferedWriter = null;

		try {

			if (!file.getParentFile().exists()) {
				if (!file.getParentFile().mkdirs())
					return false;
			}

			if (!file.exists()) {
				if (!file.createNewFile())
					return false;
			}

			bufferedWriter = new BufferedWriter(new FileWriter(file, append));
			bufferedWriter.write(content);

			returnValue = true;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			try {
				if (bufferedWriter != null)
					bufferedWriter.close();

				bufferedWriter = null;
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
		}
		return returnValue;
	}

	/**
	 * 写文件
	 * 
	 * @param filepathname
	 * @param content
	 * @param append
	 * @return
	 */
	public static boolean writeFile(String filepathname, String content, boolean append) {
		boolean returnValue = false;
		File file = null;

		try {
			file = new File(filepathname);
			returnValue = writeFile(file, content, append);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return returnValue;
	}

	/**
	 * 写文件
	 * 
	 * @param filepathname
	 * @param content
	 * @param append
	 * @return
	 */
	public static boolean writeFile(String filepathname, StringBuffer content, boolean append) {
		boolean returnValue = false;
		File file = null;

		try {
			file = new File(filepathname);
			returnValue = writeFile(file, content.toString(), append);

			if (returnValue)
				content.delete(0, content.length());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return returnValue;
	}

	/**
	 * 写文件
	 * 
	 * @param filepathname
	 * @param contentMap
	 * @param append
	 * @return
	 */
	public static boolean writeFile(String filepathname, TreeMap<String, String> contentMap, boolean append) {
		boolean returnValue = false;
		File file = null;
		Set<Map.Entry<String, String>> set = null;
		Iterator<Map.Entry<String, String>> iterator = null;
		StringBuffer contentBuffer = null;

		try {
			if (contentMap == null || contentMap.size() == 0)
				return false;

			file = new File(filepathname);
			contentBuffer = new StringBuffer();

			set = contentMap.entrySet();
			iterator = set.iterator();

			while (iterator.hasNext()) {
				Map.Entry<String, String> me = (Map.Entry<String, String>) iterator.next();
				String key = me.getKey();
				String value = contentMap.get(key);

				// System.out.println(key);

				if (value == null)
					contentBuffer.append(key + "\n");
				else
					contentBuffer.append(key + " " + value + "\n");
			}

			returnValue = writeFile(file, contentBuffer.toString(), append);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return returnValue;
	}

	public static void main(String[] args) {

		List<String> list = FileUtils.getFilePathNameList("D:\\billingfile", FileUtils.FILETYPE_FILE,
				"storage_hour_billing_", "xml");
		for (String string : list) {
			System.out.println(string);
		}
	}
}
