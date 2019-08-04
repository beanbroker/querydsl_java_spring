package com.beanbroker.sample.exception;


//InternalServerException
public class InternalServerException extends BeanbrokerException {

	private BeanbrokerError error;
	private String detailErrorMessage;


	public InternalServerException(BeanbrokerError error, String detailErrorMessage) {
		super(500, error, detailErrorMessage);
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
