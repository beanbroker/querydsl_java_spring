package com.beanbroker.sample.api.test.client;

import com.beanbroker.sample.api.user.domain.UserInfo;
import com.beanbroker.sample.util.client.SampleRestClient;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.net.URISyntaxException;

@Component
public class UserClient extends SampleRestClient {

    private String host = "http://localhost:9090";

    private String uri = "/user";

    public UserClient() {
        this.endPointHost = host;
    }

    public void createUser(UserInfo userInfo) throws URISyntaxException {



        send(uri, HttpMethod.POST, userInfo, (Class<Object>) null);

    }

    public void updateUser(UserInfo userInfo) throws URISyntaxException {

        send(uri, HttpMethod.PUT, userInfo, (Class<Object>) null);
    }

    public ResponseEntity<UserInfo> getUser(String userId) throws URISyntaxException {

        String temp = uri+ "?userId=" + userId;

        return send(temp, HttpMethod.GET , UserInfo.class);

    }

    public void removeUser() {

    }

}
