package com.beanbroker.sample.api.user.repository;

import com.beanbroker.sample.api.user.entity.QUserEntity;
import com.beanbroker.sample.api.user.entity.UserEntity;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class UserRepositoryImpl extends QuerydslRepositorySupport
        implements UserRepositoryCustom {

    private static final QUserEntity table = QUserEntity.userEntity;


    /**
     * Creates a new {@link QuerydslRepositorySupport} instance for the given domain type.
     *
     * @param domainClass must not be {@literal null}.
     */
    public UserRepositoryImpl() {
        super(UserEntity.class);
    }

    @Override
    public UserEntity getByUserId(String userId) {

        return  from(table)
                .where(table.userId.eq(userId))
                .fetchOne();

    }
}
