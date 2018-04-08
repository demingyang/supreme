package com.richgo.annotation;

import com.richgo.util.SensitiveParamUtils;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Date: 2017/8/4
 * Time: 16:48
 * User: yangkai
 * EMail: yangkai01@chtwm.com
 */
@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER, ElementType.FIELD, ElementType.METHOD})
public @interface SensitiveParam {

    boolean required() default true;

    SensitiveParamUtils.SensitiveType type() ;
}
