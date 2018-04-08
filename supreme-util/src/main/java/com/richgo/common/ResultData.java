package com.richgo.common;

import java.io.Serializable;

public class ResultData implements Serializable{

	private static final long serialVersionUID = -3412911502565715757L;
	
	
	private String status;
	
	private String message;
	
	private Object data;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	
}
