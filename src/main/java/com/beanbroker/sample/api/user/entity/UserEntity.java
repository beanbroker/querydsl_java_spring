package com.beanbroker.sample.api.user.entity;

import lombok.*;


import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "users")
@Getter
public class UserEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String userId;
    @Column
    private String name;
    @Column
    private int age;

    @Builder
    public UserEntity(String userId, String name, int age) {

        this.userId = userId;
        this.name = name;
        this.age = age;

    }

}
