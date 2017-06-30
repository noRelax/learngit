package com.ehome.core.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.BeanUtils;

/**
 * 实体类工具类
 * 
 * @Title:EntityUtils
 * @Description:TODO
 * @author:张钟武
 * @date:2017年4月18日 下午2:49:05
 * @version:
 */
public final class EntityUtils {

	private EntityUtils() {

	}

	/**
	 * 将实体类集合转化为DTO对象集合
	 * 
	 * @param entityList
	 * @param clazz
	 * @return
	 */
	public static <E, T> List<T> entityConvertDto(Collection<E> entityList,
			Class<T> clazz) {
		if (CollectionUtils.isNotEmpty(entityList)) {
			List<T> list = new ArrayList<>(entityList.size());
			try {
				for (E o : entityList) {
					T v = clazz.newInstance();
					BeanUtils.copyProperties(o, v);
					list.add(v);
				}
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
				throw new ClassCastException(e.getMessage());
			}
			return Collections.unmodifiableList(list);
		}
		return Collections.emptyList();
	}

	/**
	 * 实体对象转化
	 * @param o
	 * @param clazz
	 * @return
	 */
	public static <E, T> T convert(E o, Class<T> clazz) {
		if (o == null) {
			return null;
		}
		try {
			T v = clazz.newInstance();
			BeanUtils.copyProperties(o, v);
			return v;
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
			throw new ClassCastException(e.getMessage());
		}
	}

}
