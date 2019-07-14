package com.beanbroker.sample.api.user.service;

import com.beanbroker.sample.api.user.entity.QUserEntity;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import org.springframework.util.StringUtils;

public class UserPredicator {

    private static final QUserEntity table = QUserEntity.userEntity;

    private BooleanBuilder builder = new BooleanBuilder();


    public UserPredicator userId(String userId){

        if(userId != null){
            builder.and(table.userId.eq(userId));
        }
        return this;
    }

    public UserPredicator name(String name){

        if(name != null){
            builder.and(table.name.eq(name));
        }
        return this;
    }

    public UserPredicator age(int age){

        if(age > 0){
            builder.and(table.age.eq(age));
        }
        return this;
    }

    public Predicate values(){

        return builder.getValue();
    }
}
