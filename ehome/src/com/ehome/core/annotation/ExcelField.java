package com.ehome.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义注解-Excel字段
 * 
 * @Title:ExcelField
 * @Description:TODO
 * @author:张钟武
 * @date:2017年1月21日 下午4:17:48
 * @version:
 */
@Target({ ElementType.TYPE, ElementType.FIELD, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelField {

	/**
	 * 导入时excel列标题
	 * 
	 * @return
	 */
	String columnName() default "";

	/**
	 * 字段类型
	 * 
	 * @return
	 */
	ExcelFieldType columnType() default ExcelFieldType.STRING;
}
