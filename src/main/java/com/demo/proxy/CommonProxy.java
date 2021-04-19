package com.demo.proxy;

public class CommonProxy {

	private String message;

	private Object data;

	private Integer status;

	private Boolean flag;
	
	public CommonProxy(String message, Integer status, Boolean flag) {
		super();
		this.message = message;
		this.status = status;
		this.flag = flag;
	}

	public CommonProxy(String message, Object data, Integer status, Boolean flag) {
		super();
		this.message = message;
		this.data = data;
		this.status = status;
		this.flag = flag;
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

	public Boolean getFlag() {
		return flag;
	}

	public void setFlag(Boolean flag) {
		this.flag = flag;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
