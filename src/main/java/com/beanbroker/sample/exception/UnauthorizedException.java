package com.beanbroker.sample.exception;


//인증이 되지 않은 유저
public class UnauthorizedException extends BeanbrokerException {

    private BeanbrokerError error;
    private String detailErrorMessage;


    public UnauthorizedException(BeanbrokerError error, String detailErrorMessage) {
        super(401, error, detailErrorMessage);
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
