package com.beanbroker.sample.api.user.repository;

import com.beanbroker.sample.api.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends
        JpaRepository<UserEntity, Long>,
//        QuerydslPredicateExecutor<UserEntity>,
        UserRepositoryCustom {

}