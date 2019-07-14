package com.beanbroker.sample.api.user.domain;


import lombok.Data;

@Data
public class UserInfo {

    private String userName;
    private int userAge;


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getUserAge() {
        return userAge;
    }

    public void setUserAge(int userAge) {
        this.userAge = userAge;
    }
}
