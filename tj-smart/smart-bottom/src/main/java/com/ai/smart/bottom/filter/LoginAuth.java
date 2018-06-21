package com.ai.smart.bottom.filter;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD,ElementType.METHOD})
@Documented
public @interface LoginAuth {

    boolean isAuth() default true;
}
