package com.gsafety.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Field_Annotation {
    //用于字段
    String describe() default "<Default>";
    Class type() default void.class;
}
