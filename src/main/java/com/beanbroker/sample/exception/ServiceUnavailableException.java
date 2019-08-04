package com.beanbroker.sample.exception;


//InternalServerException
public class ServiceUnavailableException extends BeanbrokerException {

	private BeanbrokerError error;
	private String detailErrorMessage;


	public ServiceUnavailableException(BeanbrokerError error, String detailErrorMessage) {
		super(503, error, detailErrorMessage);
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
