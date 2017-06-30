package com.ehome.core.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.joda.time.DateTime;

/**
 * 日期格式工具类
 * 
 * @Title:DateFormatUtils
 * @Description:TODO
 * @author:张钟武
 * @date:2017年3月14日 上午11:30:50
 * @version:
 */
public final class DateFormatUtils extends
		org.apache.commons.lang3.time.DateFormatUtils {

	private DateFormatUtils() {

	}

	/** 日期格式：2017 */
	public static final String FORMAT_YEAR = "yyyy";

	/** 日期格式：2017年1月 */
	public static final String FORMAT_MONTH = "yyyy年MM月";

	/** 日期格式：2017-01-01 */
	public static final String FORMAT_DATE = "yyyy-MM-dd";

	/** 日期格式：20170101 */
	public static final String FORMAT_DAY = "yyyyMMdd";

	/** 日期格式：2017-01-01 10:00:00 */
	public static final String FORMAT_DATE_FULL = "yyyy-MM-dd HH:mm:ss";

	/** 日期格式：2017-01-01 10:00 */
	public static final String FORMAT_DATE_TIME = "yyyy-MM-dd HH:mm";

	/** 日期格式：10:00:00 */
	public static final String FORMAT_TIME = "HH:mm:ss";

	/**
	 * 格式化日期
	 * 
	 * @param dateTime
	 * @param pattern
	 * @return
	 */
	public static String formatDate(final DateTime dateTime,
			final String pattern) {
		return format(dateTime.toDate(), pattern, null, null);
	}

	/**
	 * 格式化日期
	 * 
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String formatDate(Date date, String pattern) {
		return format(date, pattern);
	}

	/**
	 * @Title: format
	 * @Description: 格式化时间
	 * @param dateTime
	 *            时间
	 * @param pattern
	 *            格式
	 * @return 格式化的日期
	 */
	public static String format(final DateTime dateTime, final String pattern) {
		return format(dateTime.toDate(), pattern, null, null);
	}

	/**
	 * @Title: parse
	 * @Description: 格式化日期
	 * @param source
	 * @return
	 */
	public static Date parse(String source) {
		return parse(source, FORMAT_DATE_FULL);
	}

	/**
	 * @Title: parse
	 * @Description: 格式化日期
	 * @param source
	 * @param pattern
	 * @return
	 */
	public static Date parse(String source, String pattern) {
		if (StringUtils.isNotBlank(source) && StringUtils.isNotBlank(pattern)) {
			try {
				DateFormat df = new SimpleDateFormat(pattern);
				return df.parse(source);
			} catch (ParseException e) {
			}
		}
		return null;
	}

}
