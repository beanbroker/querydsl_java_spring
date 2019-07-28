package com.beanbroker.sample.api.user.service;

import com.beanbroker.sample.api.user.entity.UserEntity;
import com.beanbroker.sample.api.user.repository.UserRepository;
import javassist.NotFoundException;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserService.class)
public class UserServiceTest {

    @MockBean
    UserRepository userRepository;


    @Autowired
    UserService userService;

    private final String TEST_USER_ID = "powertest";
    private final String TEST_USER_NAME = "룰루랄라";
    private final int TEST_USER_AGE = 13;

    @After
    public void cleanup() {

        userService.deleteUser(TEST_USER_ID);
    }

    @Test
    public void AfterSaveUser_GetUserInfo() throws NotFoundException {


        //Given 유저를 생성하였고
        userService.createUser(TEST_USER_ID, TEST_USER_NAME, TEST_USER_AGE);

        //When 유져를 찾을때
        UserEntity userEntity = userService.getUserId(TEST_USER_ID);

        //then 유저의 이름은 룰루랄라이다
        assertThat(userEntity.getName(), is(TEST_USER_NAME));


    }

    @Test(expected = NotFoundException.class)
    public void checkExpectedException() throws NotFoundException {


        //When 유져를 찾을때
        UserEntity userEntity = userService.getUserId("sadfsd");


    }



}