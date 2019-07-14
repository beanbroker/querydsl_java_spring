package com.beanbroker.sample.api.user.repository;

import com.beanbroker.sample.api.user.entity.UserEntity;

public interface UserRepositoryCustom {


    UserEntity getByUserId(String userId);
}
