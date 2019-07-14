package com.beanbroker.sample.api.user.repository;

import com.beanbroker.sample.api.user.entity.UserEntity;
import com.querydsl.core.types.Predicate;
public interface UserRepositoryCustom {


    UserEntity getByUserId(String userId);
    UserEntity getUserInfoWithPredicator(Predicate predicate);
}
