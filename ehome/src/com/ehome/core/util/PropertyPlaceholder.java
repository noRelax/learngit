/**
 * @Project:ZGHome
 * @FileName:PropertyPlaceholder.java
 */
package com.ehome.core.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

/**
 * @Title:PropertyPlaceholder
 * @Description:TODO
 * @author:ddl
 * @date:2017年1月4日
 * @version:
 */
public class PropertyPlaceholder extends PropertyPlaceholderConfigurer {

	private static Map<String, String> propertyMap;

	@Override
	protected void processProperties(
			ConfigurableListableBeanFactory beanFactoryToProcess,
			Properties props) throws BeansException {
		super.processProperties(beanFactoryToProcess, props);
		propertyMap = new HashMap<String, String>();
		for (Object key : props.keySet()) {
			String keyStr = key.toString();
			String value = props.getProperty(keyStr);
			propertyMap.put(keyStr, value);
		}
	}

	// static method for accessing context properties
	public static Object getProperty(String name) {
		if (propertyMap != null) {
			return propertyMap.get(name);
		} else {
			return null;
		}
	}
}
