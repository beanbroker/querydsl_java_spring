package com.beanbroker.sample.common.domain;

import lombok.Data;

@Data
public class ErrorResponse extends CommonResponse{

    private String errorName;
    private String errorMsg;
    private int errorCode;
    private String path;
    private String detailMessage;

    public ErrorResponse(String errorName, String errorMsg, int errorCode, String detailMessage ) {
        super();
        this.errorName = errorName;
        this.errorMsg = errorMsg;
        this.errorCode = errorCode;
        this.detailMessage = detailMessage;

    }


    public ErrorResponse(String errorName, String errorMsg, int errorCode, String path, String detailMessage) {
        super();
        this.errorName = errorName;
        this.errorMsg = errorMsg;
        this.errorCode = errorCode;
        this.path = path;
        this.detailMessage = detailMessage;

    }
}
