package com.beanbroker.sample.exception;


//로그인 실패
public class SigninFailedException extends BeanbrokerException {

	private BeanbrokerError error;
	private String detailErrorMessage;


	public SigninFailedException(BeanbrokerError error, String detailErrorMessage) {
		super(406, error, detailErrorMessage);
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
