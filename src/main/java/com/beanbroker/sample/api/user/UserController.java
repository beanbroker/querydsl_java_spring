package com.beanbroker.sample.api.user;


import com.beanbroker.sample.api.user.entity.UserEntity;
import com.beanbroker.sample.api.user.service.UserService;
import javassist.NotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/user")
    public UserEntity testUser() throws NotFoundException {



        userService.createUser();

        return userService.getUserId("beanbroker");
    }
}
