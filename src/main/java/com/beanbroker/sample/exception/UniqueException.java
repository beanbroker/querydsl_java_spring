package com.beanbroker.sample.exception;


//요청 파라미터가 고유한 값이어야 하지만 그렇지 않은 경우.
public class UniqueException extends BeanbrokerException {

	private BeanbrokerError error;
	private String detailErrorMessage;


	public UniqueException(BeanbrokerError error, String detailErrorMessage) {
		super(409, error, detailErrorMessage);
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
