package com.beanbroker.sample.util.client;

import com.google.common.base.Charsets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

@Slf4j
public class ClientErrorHandler implements ResponseErrorHandler {



    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return response.getStatusCode().series()!= HttpStatus.Series.SUCCESSFUL;
    }


    @Override
    public void handleError(ClientHttpResponse response) throws IOException {

        String resString = StreamUtils.copyToString(response.getBody(), Charsets.UTF_8);



        log.debug("start to check what is wrong ");
        log.debug("Status code  : {}", response.getStatusCode());
        log.debug("Status text  : {}", response.getStatusText());
        log.debug("Headers      : {}", response.getHeaders());
        log.debug("Response body: {}", resString);
        log.debug("end to check what is wrong");





    }
}
