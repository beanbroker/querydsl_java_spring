package com.beanbroker.sample.api.user;


import com.beanbroker.sample.api.user.domain.UserInfo;
import com.beanbroker.sample.api.user.entity.UserEntity;
import com.beanbroker.sample.api.user.service.UserService;
import javassist.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createUser(
            @RequestBody UserInfo userInfo
    ) {


        userService.createUser(
                userInfo.getUserId(),
                userInfo.getUserName(),
                userInfo.getUserAge()
        );
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public UserEntity getUser(
            @RequestParam("userId") String userId
    ) throws NotFoundException {
        return userService.getByUserId(userId);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateUser(
            @RequestBody UserInfo userInfo
    ) {

        userService.updateUser(userInfo);
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(
            @PathVariable("userId") String userId
    ) {
        userService.deleteUser(userId);
    }

    @GetMapping("/test")
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
