package com.beanbroker.sample.api.test.transaction;


import com.beanbroker.sample.api.user.repository.UserRepository;
import com.beanbroker.sample.api.user.service.FirstTestUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = FirstTestUserService.class)
public class TransactionTest {


    @MockBean
    UserRepository userRepository;


    @Autowired
    FirstTestUserService firstTestUserService;



    @Test
    public void testFirst() throws Exception {

        firstTestUserService.createUserBeforeError("test", "tete", 30);

    }
}
