package com.beanbroker.sample.api.user.service;

import com.beanbroker.sample.api.user.domain.UserInfo;
import com.beanbroker.sample.api.user.entity.UserEntity;
import com.beanbroker.sample.api.user.repository.UserRepository;
import com.querydsl.core.types.Predicate;
import javassist.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Transactional(readOnly = false)
    public void createUser(String userId, String userName, int age) {

        userRepository.save(
                UserEntity.builder().userId(userId).name(userName).age(age).build()
        );

    }

    @Transactional(readOnly = true)
    public UserEntity getUserId(String userId) throws NotFoundException {


        userRepository.getByUserId(userId);
        Optional<UserEntity> userEntity = Optional.ofNullable(userRepository.getByUserId(userId));

        if (!userEntity.isPresent()) {
            throw new NotFoundException("Not Found");
        }

        UserInfo userInfo = new UserInfo();
        userInfo.setUserAge(userEntity.get().getAge());
        userInfo.setUserName(userEntity.get().getName());

        return userEntity.get();

    }


    @Transactional(readOnly = false)
    public void deleteUser(String userId) {


        userRepository.delete(
                userRepository.getByUserId(userId));
    }


    public UserEntity getUserInfoWithPredicator(
            String userId,
            String name,
            int age

    ) {


        return userRepository.getUserInfoWithPredicator(
                setUserQuery(userId, name, age)
        );
    }


    private Predicate setUserQuery(String userId,
                                   String name,
                                   int age) {

        return new UserPredicator()
                .userId(userId)
                .name(name)
                .age(age)
                .values();

    }
}
