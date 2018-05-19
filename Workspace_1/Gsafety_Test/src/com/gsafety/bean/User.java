package com.gsafety.bean;

import com.gsafety.util.Field_Annotation;

public class User {
    //注释字段
    @Field_Annotation(describe = "编号", type = int.class)
    private int userId;
    @Field_Annotation(describe = "姓名", type = String.class)
    private String userName;

    public User(int userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
