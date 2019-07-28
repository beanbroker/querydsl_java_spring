package com.beanbroker.sample.util.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;


public abstract class SampleRestClient {


    protected String endPointHost;



    private RestTemplate client = new RestTemplate();

    private <T> RequestEntity<T> beforeSend(
            String subUri, HttpMethod method, T bodyData
    ) throws URISyntaxException {

        RequestEntity<T> request = RequestEntity.method(method, new URI(endPointHost + subUri))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(bodyData);

        client.setErrorHandler(new ClientErrorHandler());

        return request;
    }


    protected <T, S> ResponseEntity<S> send(String subUri, HttpMethod method, T bodyData, Class<S> returnClassName) throws URISyntaxException {

        ResponseEntity<S> response = client.exchange(beforeSend(subUri, method, bodyData), returnClassName);

        return response;
    }

    protected <T, S> ResponseEntity<S> send(String subUri, HttpMethod method, T bodyData, ParameterizedTypeReference<S> returnClassName) throws URISyntaxException {

        ResponseEntity<S> response = client.exchange(beforeSend(subUri, method, bodyData), returnClassName);

        return response;
    }

    private RequestEntity<Void> beforeSendGetMethod(
            String subUri, HttpMethod method
    ) throws URISyntaxException {

        RequestEntity.BodyBuilder builder = RequestEntity.method(method, new URI(endPointHost + subUri))
                .accept(MediaType.APPLICATION_JSON);

        client.setErrorHandler(new ClientErrorHandler());

        return builder.build();
    }

    protected <S> ResponseEntity<S> send(String subUri, HttpMethod method, Class<S> returnClassName) throws URISyntaxException {

        ResponseEntity<S> response = client.exchange(beforeSendGetMethod(subUri, method), returnClassName);

        return response;
    }

    protected <S> ResponseEntity<S> send(String subUri, HttpMethod method, ParameterizedTypeReference<S> returnClassName) throws URISyntaxException {

        ResponseEntity<S> response = client.exchange(beforeSendGetMethod(subUri, method), returnClassName);

        return response;
    }


}
