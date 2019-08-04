package com.beanbroker.sample.api.test.transaction;


import com.beanbroker.sample.api.user.entity.UserEntity;
import com.beanbroker.sample.api.user.repository.UserRepository;
import com.beanbroker.sample.api.test.FirstTestUserService;
import com.beanbroker.sample.exception.BadRequestException;
import com.beanbroker.sample.exception.BeanbrokerError;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest()
public class TransactionTest {


    @Autowired
    UserRepository userRepository;


    @Autowired
    FirstTestUserService firstTestUserService;

    private final String TEST_USER_ID = "powertest";
    private final String TEST_USER_NAME = "룰루랄라";
    private final int TEST_USER_AGE = 13;



    @Test()
    public void createUserAfterError() throws Exception {

        firstTestUserService.createUserAfterException(TEST_USER_ID, TEST_USER_NAME, 30);

        UserEntity userEntity = userRepository.getByUserId(TEST_USER_ID);

        //then 유저의 이름은 룰루랄라이다
        assertThat(userEntity.getName(), is(TEST_USER_NAME));


    }

    @Test(expected=Exception.class)
    public void createUserAfterAnotherBusiniessError()  {

        firstTestUserService.createUserError(TEST_USER_ID, TEST_USER_NAME, 30);

        UserEntity userEntity = userRepository.getByUserId(TEST_USER_ID);

        //then 유저의 이름은 룰루랄라이다
        assertThat(userEntity.getName(), is(TEST_USER_NAME));


    }

    private void tete(){

        throw new BadRequestException(BeanbrokerError.UNKNOWN_ERROR, "몰라");
    }



}
