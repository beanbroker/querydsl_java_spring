package com.beanbroker.sample.exception;


//요청 파라미터 중 필수인 값이 누릭인 경
public class RequiredException extends BeanbrokerException {

	private BeanbrokerError error;
	private String detailErrorMessage;


	public RequiredException(BeanbrokerError error, String detailErrorMessage) {
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
