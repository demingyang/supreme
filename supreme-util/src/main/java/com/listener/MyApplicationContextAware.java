package com.listener;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 
 * 获取ApplicationContex
 * 
 * 需要在spring-mvc.xml中添加此类bean
 * 
 */
public class MyApplicationContextAware implements ApplicationContextAware {
	/** 上下文环境 */
	private static ApplicationContext context;

	@SuppressWarnings("static-access")
	@Override
	public void setApplicationContext(ApplicationContext context) throws BeansException {
		this.context = context;
	}

	public static ApplicationContext getContext() {
		return context;
	}
}
