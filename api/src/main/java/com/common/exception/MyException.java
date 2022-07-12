package com.common.exception;

public class MyException extends Exception {
	private String id;
	private String message;
	private Integer code;

	public MyException(String id, String message) {
		this.id = id;
		this.message = message;
		this.code = 50000;
	}

	@Override
	public String getMessage() {
		return message;
	}

	public String getId() {
		return id;
	}

	public Integer getCode() {
		return code;
	}
}
