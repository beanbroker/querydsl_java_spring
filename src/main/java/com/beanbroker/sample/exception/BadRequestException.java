package com.beanbroker.sample.exception;


//일반 적인 오류
public class BadRequestException extends BeanbrokerException {

	private BeanbrokerError error;
	private String detailErrorMessage;


	public BadRequestException(BeanbrokerError error, String detailErrorMessage) {
		super(400, error, detailErrorMessage);
	}

	public BeanbrokerError getError() {
		return error;
	}

	public void setError(BeanbrokerError error) {
		this.error = error;
	}

	public String getDetailErrorMessage() {
		return detailErrorMessage;
	}

	public void setDetailErrorMessage(String detailErrorMessage) {
		this.detailErrorMessage = detailErrorMessage;
	}
}
