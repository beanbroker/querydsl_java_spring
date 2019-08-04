package com.beanbroker.sample.api.test;


import com.beanbroker.sample.api.user.entity.UserEntity;
import com.beanbroker.sample.api.user.repository.UserRepository;
import com.beanbroker.sample.exception.BeanbrokerError;
import com.beanbroker.sample.exception.BeanbrokerException;
import com.beanbroker.sample.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class FirstTestUserService {


    private final UserRepository userRepository;

    public FirstTestUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Transactional
    public void createUserAfterException(String userId, String userName, int age) {

        userRepository.save(
                UserEntity.builder().userId(userId).name(userName).age(age).build()
        );


        try {
            throw new Exception("sdf");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Transactional
    public void createUserAfterRunTimeException(String userId, String userName, int age) {

        userRepository.save(
                UserEntity.builder().userId(userId).name(userName).age(age).build()
        );


        throw new RuntimeException("sdf");

    }


    @Transactional(rollbackFor = BeanbrokerException.class)
    public void createUserError(String userId, String userName, int age) {

        userRepository.save(
                UserEntity.builder().userId(userId).name(userName).age(age).build()
        );
        makeUserError();


    }

    public void makeUserError() {
        throw new NotFoundException(BeanbrokerError.SERVICE_UNVAILABLE, "df");
    }


    public void testCreateUser(String userId, String userName, int age) {

        userRepository.save(
                UserEntity.builder().userId(userId).name(userName).age(age).build()
        );


    }
}

