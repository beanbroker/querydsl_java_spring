package com.beanbroker.sample.api.test;

import com.beanbroker.sample.api.test.client.UserClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;

@RestController
@RequestMapping("/test")
public class TestController {

    private final UserClient userClient;

    public TestController(UserClient userClient) {
        this.userClient = userClient;
    }

    @PostMapping
    public void testCreate() throws URISyntaxException {


    }

    @GetMapping
    public void testGet(){

    }

    @PutMapping
    public void testPut(){

    }

    @DeleteMapping
    public void testDelete(){

    }

}
