package com.gpmall.commons.lock.annotation;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CustomerLock {


    String lockKey();


    String lockSuffix() default "";


    String lockPrefix() default "";



    String separator() default "#";


    String lockType() default "";


    boolean tryLock() default false;


    int waitTime() default 0;


    int leaseTime() default 30;


    TimeUnit timeUnit() default TimeUnit.SECONDS;


}
