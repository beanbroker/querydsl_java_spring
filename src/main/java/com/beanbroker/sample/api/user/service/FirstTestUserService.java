package com.beanbroker.sample.api.user.service;


import com.beanbroker.sample.api.user.entity.UserEntity;
import com.beanbroker.sample.api.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FirstTestUserService {


    private final UserRepository userRepository;

    public FirstTestUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = false)
    public void createUserBeforeError(String userId, String userName, int age) throws Exception {

        userRepository.save(
                UserEntity.builder().userId(userId).name(userName).age(age).build()
        );

        throw new Exception("sdf");
    }



}

