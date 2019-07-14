package com.beanbroker.sample.api.user.service;

import com.beanbroker.sample.api.user.domain.UserInfo;
import com.beanbroker.sample.api.user.entity.UserEntity;
import com.beanbroker.sample.api.user.repository.UserRepository;
import com.querydsl.core.types.Predicate;
import javassist.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public void createUser(){


        userRepository.save(
                new UserEntity("beanbroker", "pkj", 32)
        );

    }

    public UserEntity getUserId(String userId) throws NotFoundException {


        Optional<UserEntity> userEntity = Optional.ofNullable(userRepository.getByUserId(userId));

        if(!userEntity.isPresent()){
            throw new NotFoundException("Not Found");
        }

        UserInfo userInfo = new UserInfo();
        userInfo.setUserAge(userEntity.get().getAge());
        userInfo.setUserName(userEntity.get().getName());

        return userEntity.get();

    }


    public UserEntity getUserInfoWithPredicator(
            String userId,
            String name,
            int age

    ){



        return userRepository.getUserInfoWithPredicator(
                setUserQuery(userId, name, age)
        );
    }


    private Predicate setUserQuery( String userId,
                                    String name,
                                    int age){

        return new UserPredicator()
                .userId(userId)
                .name(name)
                .age(age)
                .values();

    }
}
