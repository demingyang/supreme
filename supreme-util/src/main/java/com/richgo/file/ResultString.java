package com.richgo.file;

/**
 * 
 * 上传文件返回类型，上传成功后 fileName, fileExtName, groupName, fastdfsPath都有值
 * 
 * @author zhouxy
 *
 */
public class ResultString {
	/* 对应CodeConts类中状态码 */
	private String code = null;
	/* 文件真实名称 */
	private String originalFilename = null;
	/* 文件类型 */
	private String fileExtName = null;
	/* group名称 */
	private String groupName = null;
	/* 文件在fastdfs相对路径（文件名称文件系统重命名了） */
	private String fastdfsPath = null;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getOriginalFilename() {
		return originalFilename;
	}

	public void setOriginalFilename(String originalFilename) {
		this.originalFilename = originalFilename;
	}

	public String getFileExtName() {
		return fileExtName;
	}

	public void setFileExtName(String fileExtName) {
		this.fileExtName = fileExtName;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getFastdfsPath() {
		return fastdfsPath;
	}

	public void setFastdfsPath(String fastdfsPath) {
		this.fastdfsPath = fastdfsPath;
	}
}
