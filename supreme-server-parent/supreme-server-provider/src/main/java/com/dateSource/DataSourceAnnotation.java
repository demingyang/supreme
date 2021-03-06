package com.dateSource;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * 数据库注解类型
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface DataSourceAnnotation {
	// 默认多金数据源
	String name() default DataSourceAnnotation.dataSourceDJ;

	// 多金对应数据源
	public static String dataSourceDJ = "dataSourceDJ";
	// HR对应数据源
	public static String dataSourceHR = "dataSourceHR";
	// 多金中间库对应数据源
	public static String dataSourceDJM = "dataSourceDJM";

}
