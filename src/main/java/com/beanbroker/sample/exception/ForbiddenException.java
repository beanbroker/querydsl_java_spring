package com.beanbroker.sample.exception;


//인증은 되었으나 권한이 없는 경우
public class ForbiddenException extends BeanbrokerException {

    private BeanbrokerError error;
    private String detailErrorMessage;


    public ForbiddenException(BeanbrokerError error, String detailErrorMessage) {
        super(403, error, detailErrorMessage);
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
