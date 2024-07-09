package com.gpmall.commons.lock.extension.annotation;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LockSpi {


    String value() default "";

}
