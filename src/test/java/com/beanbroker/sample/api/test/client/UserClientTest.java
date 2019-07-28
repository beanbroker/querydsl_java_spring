package com.beanbroker.sample.api.test.client;

import com.beanbroker.sample.api.user.domain.UserInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URISyntaxException;

@RunWith(SpringRunner.class)
@SpringBootTest()
public class UserClientTest {


    @Autowired
    UserClient userClient;

    @Test
    public void testCreate() throws URISyntaxException {

        UserInfo userInfo = new UserInfo();

        userInfo.setUserId("haha");
        userInfo.setUserName("귀찮다");
        userInfo.setUserAge(45);

        userClient.createUser(userInfo);
    }


    @Test
    public void testGet() throws URISyntaxException {


        ResponseEntity<UserInfo> haaha = userClient.getUser("haha");

        System.out.println(haaha);
    }
}