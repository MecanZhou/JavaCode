package com.gsafety.test.unit16;

import com.gsafety.bean.User;
import com.gsafety.util.Field_Annotation;

import java.lang.reflect.Field;

public class Test16_2 {

    //判断用户信息是否符合规范

    public static void main(String[] args) {
        User user = new User(1, "Andy");
        Class userC = user.getClass();
        Field[] declaredFields = userC.getDeclaredFields();
        for (int i = 0; i < declaredFields.length; i++) {
            Field field = declaredFields[i];
            field.setAccessible(true);
            if (field.isAnnotationPresent(Field_Annotation.class)){
                System.out.println("用户信息符合规范：");
                Field_Annotation fa = field.getAnnotation(Field_Annotation.class);
                System.out.println("字段"+i+"描述："+fa.describe());
                System.out.println("字段"+i+"类型："+fa.type());
            }else {
                System.out.println("用户信息不符合规范！");
            }
        }
    }
}
