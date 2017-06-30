package com.ehome.core.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 集合类工具类
 * 
 * @Title:CollectionUtils
 * @Description:TODO
 * @author:张钟武
 * @date:2017年1月21日 下午4:10:08
 * @version:
 */
public final class CollectionUtils extends
		org.springframework.util.CollectionUtils {
	private CollectionUtils() {
	}

	/**
	 * @Title: isNotEmpty
	 * @Description: 判断非空集合
	 * @param collection
	 *            集合对象
	 * @return 是否为空
	 */
	public static Boolean isNotEmpty(Collection<?> collection) {
		return !isEmpty(collection);
	}

	/**
	 * @Title: isNotEmpty
	 * @Description: 判断非空集合
	 * @param map
	 *            集合对象
	 * @return 是否为空
	 */
	public static Boolean isNotEmpty(Map<?, ?> map) {
		return !isEmpty(map);
	}

	/**
	 * @Title: isEmpty
	 * @Description: 判断非空集合
	 * @param map
	 *            集合对象
	 * @return 是否为空
	 */
	public static boolean isEmpty(Object[] array) {
		return (array == null || array.length == 0);
	}

	/**
	 * @Title: union
	 * @Description: 取数组并集
	 * @param arr1
	 * @param arr2
	 * @return
	 */
	public static Integer[] union(Integer[] arr1, Integer[] arr2) {
		Set<Integer> set = new HashSet<>();
		for (Integer num : arr1) {
			set.add(num);
		}
		for (Integer num : arr2) {
			set.add(num);
		}
		Integer[] result = {};
		return set.toArray(result);
	}

	/**
	 * @Title: intersect
	 * @Description: 取数组交集
	 * @param arr1
	 * @param arr2
	 * @return
	 */
	public static Integer[] intersect(Integer[] arr1, Integer[] arr2) {
		List<Integer> list = new LinkedList<>();
		Set<Integer> set = new HashSet<>();
		for (Integer num : arr1) {
			if (!list.contains(num)) {
				list.add(num);
			}
		}
		for (Integer num : arr2) {
			if (list.contains(num)) {
				set.add(num);
			}
		}
		Integer[] result = {};
		return set.toArray(result);
	}

	/**
	 * @Title: substract
	 * @Description: 取数组差集
	 * @param arr1
	 * @param arr2
	 * @return
	 */
	public static Integer[] substract(Integer[] arr1, Integer[] arr2) {
		LinkedList<Integer> list = new LinkedList<>();
		for (Integer num : arr1) {
			if (!list.contains(num)) {
				list.add(num);
			}
		}
		for (Integer num : arr2) {
			if (list.contains(num)) {
				list.remove(num);
			}
		}
		Integer[] result = {};
		return list.toArray(result);
	}

	/**
	 * @Title: removeNull
	 * @Description: 去除Null元素
	 * @param collection
	 * @return
	 */
	public static <T> List<T> removeNull(List<T> collection) {
		List<T> nullArr = new ArrayList<>();
		nullArr.add(null);
		collection.removeAll(nullArr);
		return collection;
	}
}
