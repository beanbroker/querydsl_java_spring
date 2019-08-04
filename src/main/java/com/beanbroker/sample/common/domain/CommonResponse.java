package com.beanbroker.sample.common.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommonResponse {

    public CommonResponse() {
        this.isSuccess = true;
        this.time = LocalDateTime.now();
    }


    private boolean isSuccess;
    private LocalDateTime time;
}
