package com.common.controller;

import java.lang.annotation.*;

@Documented
@Target(ElementType.METHOD)
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface IsCheckUserLogin {
	boolean check() default false;
}
