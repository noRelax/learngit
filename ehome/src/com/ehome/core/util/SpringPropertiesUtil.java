package com.ehome.core.util;

import java.io.IOException;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

/**
 * 利用spring的resource读取配置文件
 * 
 * @author hsu
 *
 */
public class SpringPropertiesUtil {

	private final static String defaultProperties = "info.properties";

	public static Properties readProperties() {
		return readProperties(defaultProperties);
	}

	public static Properties readProperties(String pName) {
		if (StringUtils.isBlank(pName)) {
			pName = defaultProperties;
		}
		Resource resource = new ClassPathResource(pName);
		try {
			return PropertiesLoaderUtils.loadProperties(resource);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String get(Object key) {
		return get(null, key);
	}

	public static String get(Properties properties, Object key) {
		if (null == properties) {
			properties = readProperties();
		}
		return properties.containsKey(key) ? properties.get(key).toString()
				: null;
	}

	public static void main(String[] args) {
		System.out.println(readProperties());
		System.out.println(readProperties("info.properties"));
		System.out.println(readProperties("qqq"));
		System.out.println(get("testkey"));
		System.out.println(get("testkey1"));
		System.out.println(get(null, "testkey"));
		System.out.println(get(null, "testkey1"));
		System.out.println(get(readProperties(), "testkey"));
		System.out.println(get(readProperties(), "testkey1"));
		System.out.println(get(readProperties("xxxx"), "testkey1"));
	}

}
