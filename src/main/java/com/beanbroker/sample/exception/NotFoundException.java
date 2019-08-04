package com.beanbroker.sample.exception;


//해당 리소스가 없는 경우
public class NotFoundException extends BeanbrokerException {

	private BeanbrokerError error;
	private String detailErrorMessage;


	public NotFoundException(BeanbrokerError error, String detailErrorMessage) {
		super(404, error, detailErrorMessage);
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
