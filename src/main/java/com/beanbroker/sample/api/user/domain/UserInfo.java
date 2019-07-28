package com.beanbroker.sample.api.user.domain;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class UserInfo {

    private String userId;
    private String userName;
    private int userAge;



}
