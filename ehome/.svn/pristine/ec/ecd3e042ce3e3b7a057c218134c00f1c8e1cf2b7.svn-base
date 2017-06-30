package com.ehome.core.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtilsBean;

/**
 * 
 * @Title:BeanToMapUtil
 * @Description:TODO
 * @author:zsh
 * @date:2017年2月7日
 * @version 1.0,2017年2月7日
 * @{tags
 */
public class BeanToMapUtil {
	/**
	 * 
	 * 
	 * Map转换层Bean，使用泛型免去了类型转换的麻烦。
	 * 
	 * @param <T>
	 * @param map
	 * @param class1
	 * @return
	 */
	public static <T> T map2Bean(Map<String, String> map, Class<T> class1) {
		T bean = null;
		try {
			bean = class1.newInstance();
			BeanUtils.populate(bean, map);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return bean;
	}

	public static Object mapToBean(Class<?> class1, Map map) {
		Object bean = null;
		try {
			bean = class1.newInstance();
			BeanUtils.populate(bean, map);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return bean;
	}
	
	/**
	 * bean转为map
	 * @param obj
	 * @return
	 */
	public static Map<String, Object> beanToMap(Object obj) { 
        Map<String, Object> params = new HashMap<String, Object>(0); 
        try { 
            PropertyUtilsBean propertyUtilsBean = new PropertyUtilsBean(); 
            PropertyDescriptor[] descriptors = propertyUtilsBean.getPropertyDescriptors(obj); 
            for (int i = 0; i < descriptors.length; i++) { 
                String name = descriptors[i].getName(); 
                if (!"class".equals(name)) { 
                    params.put(name, propertyUtilsBean.getNestedProperty(obj, name)); 
                } 
            } 
        } catch (Exception e) { 
            e.printStackTrace(); 
        } 
        return params; 
}
}