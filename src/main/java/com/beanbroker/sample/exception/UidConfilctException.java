package com.beanbroker.sample.exception;


//유저의 uid가 중복으로 가입을 할경우
public class UidConfilctException extends BeanbrokerException {

	private BeanbrokerError error;
	private String detailErrorMessage;


	public UidConfilctException(BeanbrokerError error, String detailErrorMessage) {
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
