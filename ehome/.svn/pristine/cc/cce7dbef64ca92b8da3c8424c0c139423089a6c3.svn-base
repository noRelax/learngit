package com.ehome.core.util;

import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;

/**
 * 
 * @Title:NumberUtils
 * @Description:TODO
 * @author:张钟武
 * @date:2017年1月21日 下午4:10:01
 * @version:
 */
public final class NumberUtils extends
		org.apache.commons.lang3.math.NumberUtils {
	private NumberUtils() {
	}

	/**
	 * 字符串转 Integer
	 * 
	 * @param str
	 * @return
	 */
	public static Integer toInteger(String str) {
		try {
			if (StringUtils.isNotBlank(str)) {
				return Integer.valueOf(str);
			}
		} catch (NumberFormatException e) {
		}
		return null;
	}

	public static boolean isNull(Integer obj) {
		return obj == null;
	}

	/**
	 * @param num
	 * @return Integer.valueOf(0).equals(num)
	 */
	public static boolean eqZero(Integer num) {
		return eq(NumberUtils.INTEGER_ZERO, num);
	}

	/**
	 * 
	 * @param num
	 * @return num == 0
	 */
	public static boolean eqZero(int num) {
		return eq(0, num);
	}

	/**
	 * @param num
	 * @return !Integer.valueOf(0).equals(num)
	 */
	public static boolean neZero(Integer num) {
		return !eq(NumberUtils.INTEGER_ZERO, num);
	}

	/**
	 * @param num
	 * @return num != 0
	 */
	public static boolean neZero(int num) {
		return !eq(0, num);
	}

	/**
	 * @param num
	 * @return num == 1
	 */
	public static boolean eqOne(int num) {
		return eq(1, num);
	}

	/**
	 * @param num
	 * @return Integer.valueOf(1).equals(num)
	 */
	public static boolean eqOne(Integer num) {
		return eq(NumberUtils.INTEGER_ONE, num);
	}

	/**
	 * @param num
	 * @return num != 1
	 */
	public static boolean neOne(int num) {
		return !eq(1, num);
	}

	/**
	 * @param num
	 * @return !Integer.valueOf(1).equals(num)
	 */
	public static boolean neOne(Integer num) {
		return !eq(NumberUtils.INTEGER_ONE, num);
	}

	/**
	 * @param num1
	 * @param num2
	 * @return num1.equals(num2)
	 */
	public static boolean eq(Integer num1, Integer num2) {
		return num1.equals(num2);
	}

	/**
	 * @param num1
	 * @param num2
	 * @return num1 == num2
	 */
	public static boolean eq(int num1, int num2) {
		return num1 == num2;
	}

	/**
	 * 
	 * @return
	 *
	 */
	public static int getRandomNum() {
		Random r = new Random();
		return r.nextInt(900000) + 100000;// (Math.random()*(999999-100000)+100000)
	}

	public static String getRandomString() {
		return RandomStringUtils.randomAlphanumeric(6);
	}

	/**
	 * 计算两个数的比例
	 * 
	 * @param m
	 * @param n
	 * @return
	 */
	public static int getMaxGY(int m, int n) {
		// 求最大公约数
		if (m == n) {
			return n;
		} else {
			while (m % n != 0) {
				int temp = m % n;
				m = n;
				n = temp;
			}
			return n;
		}
	}
}
