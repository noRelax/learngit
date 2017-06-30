/**
 * @Project:ZGHome
 * @FileName:ClassUtil.java
 */
package com.ehome.core.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ehome.cloud.sys.controller.AppUserController;

/**
 * @Title:ClassUtil
 * @Description:TODO
 * @author:ddl
 * @date:2017年1月4日
 * @version:
 */
public class ClassUtil {

	/**
	 * 获取当前控制器名字部分
	 *
	 * @param controllerName
	 * @return
	 */
	public static String getName(String controllerName) {
		String classname = controllerName.substring(controllerName
				.lastIndexOf(".") + 1);
		String name = classname.replace("Controller", "");
		return name;
	}

	public static void main(String[] args) {

		Class<?> cls = AppUserController.class;
		// version1
		Annotation cc = AnnotationUtils.findAnnotation(cls,
				RequestMapping.class);
		if (cc != null) {
			String[] values = ((RequestMapping) cc).value();
			for (String s : values) {
				System.out.println(s);
			}
		}
		// version2
		// 获取指定类的所有Annotation
		Annotation[] classAnnotation = cls.getAnnotations();
		for (Annotation a : classAnnotation) {
			if (a instanceof RequestMapping) {
				// 获取指定标注的所有值
				String[] v = ((RequestMapping) a).value();
				for (String s : v) {
					System.out.println(s);
				}
				Method[] methods = cls.getMethods();
				List<String> stringList = new ArrayList<>();
				for (Method method : methods) {
					// 获取注解
					RequestMapping annotation = method
							.getAnnotation(RequestMapping.class);
					if (annotation != null) {
						// 获取注解的value值
						String[] value = annotation.value();
						for (String string : value) {
							stringList.add(string);
						}
					}
				}
			}
		}
	}
}
