package com.http;

/**
 * 
 * 返回数据bean
 * 
 * @author zhouxy
 * 
 */
public class HttpResultBean {
	/** http状态码。请求 200：请求成功 */
	protected int httpStatus = -1;
	/** 返回内容 */
	protected String resultContext = null;

	public int getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(int httpStatus) {
		this.httpStatus = httpStatus;
	}

	public String getResultContext() {
		return resultContext;
	}

	public void setResultContext(String resultContext) {
		this.resultContext = resultContext;
	}

	/**
	 * 
	 * 字段信息
	 * 
	 * @return String
	 */
	public String info() {
		StringBuffer buff = new StringBuffer();
		buff.append("httpStatus=");
		buff.append(httpStatus);
		buff.append(";resultContext=");
		buff.append(resultContext);
		return buff.toString();
	}
}
