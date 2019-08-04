package com.beanbroker.sample.exception;

public enum BeanbrokerError {
	
	BAD_REQUEST(400, "Bad Request."),
	NOT_FOUND(404, "Not Found"),
	NOT_FOUND_USER(404, "NOT_FOUND_USER"),
	PARAM_REQUIRED(400, "PARAM_REQUIRED"),
	INVALID_DATA_TYPE(400, "INVALID_DATA_TYPE"),
	INVALID_UNIQUE_PARAM(409, "INVALID_UNIQUE_PARAM"),
	FORBIDDEN(403, "FORBIDDEN"),
	ACCESS_DENIED(401, "ACCESS_DENIED"),
	SERVER_TIMEOUT(408, "SERVER_TIMEOUT"),
	INTERNAL_SERVER_ERROR(500, "INTERNAL_SERVER_ERROR"),
	SERVICE_UNVAILABLE(503, "SERVICE_UNVAILABLE"),


	UNKNOWN_ERROR(9999, "Unknown Error");
	
	private final int code;
	private final String message;
	
	private BeanbrokerError(int code, String message) {
		this.code = code;
		this.message = message;
	}
	
	public int getCode() {
		return code;
	}
	
	public String getMessage() {
		return message;
	}
	
}
