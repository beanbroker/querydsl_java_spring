package com.beanbroker.sample.exception;


//요청 파라미터 형식이나 포멧이 맞지 않는 경우
public class DataTypeException extends BeanbrokerException {

	private BeanbrokerError error;
	private String detailErrorMessage;


	public DataTypeException(BeanbrokerError error, String detailErrorMessage) {
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
