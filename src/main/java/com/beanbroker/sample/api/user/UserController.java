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


    @GetMapping("/user/test")
    public UserEntity testPredicate() throws NotFoundException {


        UserEntity test1 = userService.getUserInfoWithPredicator(
                "beanbroker",
                null,
                0

        );

        System.out.println(test1.toString());


        UserEntity test2 = userService.getUserInfoWithPredicator(
                "beanbroker",
                    "pkj",
                0

        );


        System.out.println(test2.toString());

        UserEntity test3 = userService.getUserInfoWithPredicator(
                null,
                null,
                32

        );

        System.out.println(test3.toString());


        return test3;
    }
}
