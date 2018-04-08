package com.richgo.interceptor.comment;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * 敏感词判断注解
 * 
 * 默认检查请求参数名称为“comment”对应值是否存在敏感词，使用者也可以通过"requestParamName"方法指定请求参数名称
 * 
 * @author zhouxy
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SensitiveWords {

	/**
	 * 
	 * 检查是否含有敏感词
	 * 
	 */
	boolean check() default false;

	/**
	 * 
	 * 此值为request的参数名称，不填写默认参数名称为“comment”
	 * 
	 * @return String
	 */
	String requestParamName() default "comment";
}