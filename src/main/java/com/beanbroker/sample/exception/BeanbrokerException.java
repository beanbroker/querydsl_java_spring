package com.beanbroker.sample.exception;


public class BeanbrokerException extends RuntimeException {

    private int statusCode;
    private int errorCode;
    private String errorMessage;
    private String detailMessage;

    public BeanbrokerException(BeanbrokerError error) {
        this.statusCode = 500;
        this.errorCode = error.getCode();
        this.errorMessage = error.getMessage();
        this.detailMessage = "관리자 측으로 문의주세요";
    }

    public BeanbrokerException(int code, BeanbrokerError error, String detailMessage) {
        this.statusCode = code;
        this.errorCode = error.getCode();
        this.errorMessage = error.getMessage();
        this.detailMessage = detailMessage;
    }


    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getDetailMessage() {
        return detailMessage;
    }

    public void setDetailMessage(String detailMessage) {
        this.detailMessage = detailMessage;
    }
}
