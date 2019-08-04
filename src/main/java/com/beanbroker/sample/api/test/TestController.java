package com.beanbroker.sample.api.test;

import com.beanbroker.sample.api.test.client.UserClient;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;

@RestController
@RequestMapping("/test")
public class TestController {

    private final UserClient userClient;


    private final FirstTestUserService firstTestUserService;
    public TestController(UserClient userClient, FirstTestUserService firstTestUserService) {
        this.userClient = userClient;
        this.firstTestUserService = firstTestUserService;
    }

    private final String TEST_USER_ID = "powertest";
    private final String TEST_USER_NAME = "룰루랄라";
    private final int TEST_USER_AGE = 13;

    @PostMapping
    public void testCreate()  {


    }

    @GetMapping
    public void testGet(){

//        firstTestUserService.createUserError(TEST_USER_ID, TEST_USER_NAME, 30);
    }

    @PutMapping
    public void testPut(){

    }

    @DeleteMapping
    public void testDelete(){

    }

}
