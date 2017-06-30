package com.ehome.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义注解-常数代码
 * 
 * @Title:CodeType
 * @Description:TODO
 * @author:张钟武
 * @date:2017年1月21日 下午4:16:33
 * @version:
 */
@Target({ ElementType.TYPE, ElementType.FIELD, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface CodeField {

	/**
	 * 常数代码
	 * 
	 * @return
	 */
	public String code() default "";

	/**
	 * 绑定字段
	 * 
	 * @return
	 */
	public String renderField() default "";
}
