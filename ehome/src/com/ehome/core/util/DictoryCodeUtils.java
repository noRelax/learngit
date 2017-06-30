package com.ehome.core.util;

import java.lang.reflect.Field;
import java.util.Collection;

import com.ehome.cloud.sys.service.IDictionaryService;
import com.ehome.core.annotation.CodeField;
import com.ehome.core.frame.SpringContextHolder;

/**
 * 常数代码工具类
 * 
 * @Title:DictoryCodeUtils
 * @Description:TODO
 * @author:张钟武
 * @date:2017年2月20日 下午6:45:23
 * @version:
 */
public final class DictoryCodeUtils {

	private DictoryCodeUtils() {
	}

	public static void renderCode(Object obj) {
		try {
			Field[] fields = obj.getClass().getDeclaredFields();
			for (Field field : fields) {
				CodeField codeField = field.getAnnotation(CodeField.class);
				if (null != codeField) {
					field.setAccessible(true);
					Object value = field.get(obj);
					String fieldValue = null;
					if (value != null && value instanceof String
							&& StringUtils.isNotBlank((String) value)) {
						fieldValue = value.toString();
					} else if (value != null && value instanceof Integer) {
						fieldValue = String.valueOf(value);
					} else {
						continue;
					}
					String render = codeField.renderField();
					String codeType = codeField.code();
					Field renderField = obj.getClass().getDeclaredField(render);
					renderField.setAccessible(true);
					IDictionaryService dictionaryService = SpringContextHolder
							.getBean(IDictionaryService.class);
					String renderValue = dictionaryService.getRenderFieldValue(
							codeType, fieldValue);
					if (StringUtils.isNotBlank(renderValue)) {
						renderField.set(obj, renderValue);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void renderList(Collection<?> list) {
		if (CollectionUtils.isNotEmpty(list)) {
			for (Object obj : list) {
				renderCode(obj);
			}
		}
	}
}
