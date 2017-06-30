package com.ehome.core.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 日期操作工具类
 * 
 * @Title:DateUtils
 * @Description:TODO
 * @author:张钟武
 * @date:2017年3月14日 下午12:09:55
 * @version:
 */
public final class DateUtils extends org.apache.commons.lang3.time.DateUtils {

	private DateUtils() {

	}

	/**
	 * 获取当前日期时间格式如：yyyy-MM-dd HH:mm:ss
	 * 
	 * @return
	 */
	public static String getTime() {
		return DateFormatUtils.formatDate(new Date(),
				DateFormatUtils.FORMAT_DATE_FULL);
	}

	/**
	 * 获取指定日期时间格式如：yyyy-MM-dd HH:mm:ss
	 * 
	 * @param date
	 * @return
	 */
	public static String getTime(Date date) {
		return DateFormatUtils.formatDate(date,
				DateFormatUtils.FORMAT_DATE_FULL);
	}

	/**
	 * 获取指定日期格式如：yyyy-MM-dd
	 * 
	 * @param date
	 * @return
	 */
	public static String getDay(Date date) {
		return DateFormatUtils.formatDate(date, DateFormatUtils.FORMAT_DATE);
	}

	/**
	 * 获取当前日期格式如：yyyy-MM-dd
	 * 
	 * @return
	 */
	public static String getDay() {
		return DateFormatUtils.formatDate(new Date(),
				DateFormatUtils.FORMAT_DATE);
	}

	/**
	 * 格式化指定日期字符串 格式：yyyy-MM-dd
	 * 
	 * @param date
	 * @return
	 */
	public static Date getDate(String date) {
		return DateFormatUtils.parse(date, DateFormatUtils.FORMAT_DATE);
	}

	/**
	 * @Title: compareDate
	 * @Description: TODO(日期比较，如果s>=e 返回true 否则返回false)
	 * @param s
	 * @param e
	 * @return boolean
	 * @throws
	 * @author luguosui
	 */
	public static boolean compareDate(String s, String e) {
		if (fomatDate2(s) == null || fomatDate2(e) == null) {
			return false;
		}
		return fomatDate2(s).getTime() >= fomatDate2(e).getTime();
	}

	/**
	 * 格式化日期 yyyy-MM-dd
	 * 
	 * @return
	 */
	public static Date fomatDate(String date) {
		return DateFormatUtils.parse(date, DateFormatUtils.FORMAT_DATE);
	}

	/**
	 * 格式化日期 yyyy-MM-dd HH:mm:ss
	 * 
	 * @return
	 */
	public static Date fomatDate2(String date) {
		return DateFormatUtils.parse(date, DateFormatUtils.FORMAT_DATE_FULL);
	}

	/**
	 * 格式化日期 yyyy-MM-dd HH:mm
	 * 
	 * @return
	 */
	public static Date fomatDate3(String date) {
		return DateFormatUtils.parse(date, DateFormatUtils.FORMAT_DATE_TIME);
	}

	/**
	 * 校验日期是否合法
	 * 
	 * @return
	 */
	public static boolean isValidDate(String s) {
		DateFormat fmt = new SimpleDateFormat(DateFormatUtils.FORMAT_DATE);
		try {
			fmt.parse(s);
			return true;
		} catch (Exception e) {
			// 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
			return false;
		}
	}

	/**
	 * 
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public static int getDiffYear(String startTime, String endTime) {
		Date start = DateFormatUtils.parse(startTime,
				DateFormatUtils.FORMAT_DATE);
		Date end = DateFormatUtils.parse(endTime, DateFormatUtils.FORMAT_DATE);
		try {
			int years = (int) (((end.getTime() - start.getTime()) / (1000 * 60 * 60 * 24)) / 365);
			return years;
		} catch (Exception e) {
			// 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
			return 0;
		}
	}

	/**
	 * <li>功能描述：时间相减得到天数
	 * 
	 * @param beginDateStr
	 * @param endDateStr
	 * @return long
	 * @author Administrator
	 */
	public static long getDaySub(String beginDateStr, String endDateStr) {
		long day = 0;
		java.util.Date beginDate = DateFormatUtils.parse(beginDateStr,
				DateFormatUtils.FORMAT_DATE);
		java.util.Date endDate = DateFormatUtils.parse(endDateStr,
				DateFormatUtils.FORMAT_DATE);
		day = (endDate.getTime() - beginDate.getTime()) / (24 * 60 * 60 * 1000);
		// System.out.println("相隔的天数="+day);
		return day;
	}

	/**
	 * 得到n天之后的日期
	 * 
	 * @param days
	 * @return
	 */
	public static String getAfterDayDate(String days) {
		int daysInt = Integer.parseInt(days);
		Calendar canlendar = Calendar.getInstance(); // java.util包
		canlendar.add(Calendar.DATE, daysInt); // 日期减 如果不够减会将月变动
		Date date = canlendar.getTime();
		return DateFormatUtils.formatDate(date,
				DateFormatUtils.FORMAT_DATE_FULL);
	}

	/**
	 * 得到n天之后是周几
	 * 
	 * @param days
	 * @return
	 */
	public static String getAfterDayWeek(String days) {
		int daysInt = Integer.parseInt(days);
		Calendar canlendar = Calendar.getInstance(); // java.util包
		canlendar.add(Calendar.DATE, daysInt); // 日期减 如果不够减会将月变动
		Date date = canlendar.getTime();
		return DateFormatUtils.formatDate(date, "E");
	}

	/**
	 * 
	 * @param smdate
	 * @param bdate
	 * @return
	 * @throws 计算两个时间
	 */
	public static int daysBetween2(String smdate, String bdate) {
		if (smdate == null || smdate.equals("") || bdate == null
				|| bdate.equals("")) {
			return -1;
		}
		java.util.Date nowdate1 = null;
		java.util.Date nowdate2 = null;
		long between_days = 0l;
		nowdate1 = DateFormatUtils.parse(smdate, DateFormatUtils.FORMAT_DATE);
		nowdate2 = DateFormatUtils.parse(bdate, DateFormatUtils.FORMAT_DATE);
		Calendar cal = Calendar.getInstance();
		cal.setTime(nowdate1);
		long time1 = cal.getTimeInMillis();
		cal.setTime(nowdate2);
		long time2 = cal.getTimeInMillis();
		between_days = (time2 - time1) / (1000 * 3600 * 24);
		return java.lang.Math
				.abs(Integer.parseInt(String.valueOf(between_days)));
	}

	/**
	 * 
	 * @param smdate
	 * @param bdate
	 * @return
	 */
	public static int daysBetween(String smdate, String bdate) {
		if (smdate == null || smdate.equals("") || bdate == null
				|| bdate.equals("")) {
			return -1;
		}
		java.util.Date nowdate1 = null;
		java.util.Date nowdate2 = null;
		long between_days = 0l;
		nowdate1 = DateFormatUtils.parse(smdate, DateFormatUtils.FORMAT_DAY);
		nowdate2 = DateFormatUtils.parse(bdate, DateFormatUtils.FORMAT_DAY);
		Calendar cal = Calendar.getInstance();
		cal.setTime(nowdate1);
		long time1 = cal.getTimeInMillis();
		cal.setTime(nowdate2);
		long time2 = cal.getTimeInMillis();
		between_days = (time2 - time1) / (1000 * 3600 * 24);
		return java.lang.Math
				.abs(Integer.parseInt(String.valueOf(between_days)));
	}

	/**
	 * Date或者String转化为时间戳
	 * 
	 * @param time
	 * @return 秒数
	 */
	public static long date2second(String time) {
		Date date = DateFormatUtils.parse(time,
				DateFormatUtils.FORMAT_DATE_FULL);
		return date.getTime() / 1000;
	}

	/**
	 * Date或者String转化为时间戳
	 * 
	 * @param time
	 *            毫秒数/秒数
	 * @return
	 */
	public static String second2date(long time) {
		String d = null;
		if (time / 1000 > 1000000000) { // 毫秒数
			d = DateFormatUtils.format(time, DateFormatUtils.FORMAT_DATE_FULL);
		} else { // 秒数
			d = DateFormatUtils.format(Long.valueOf(time * 1000),
					DateFormatUtils.FORMAT_DATE_FULL);
		}
		return d;
	}

	/**
	 * Date或者String转化为时间戳
	 * 
	 * @param time
	 *            毫秒数/秒数
	 * @return
	 */
	public static String second2dayDate(long time) {
		String d = null;
		if (time / 1000 > 1000000000) { // 毫秒数
			d = DateFormatUtils.format(time, DateFormatUtils.FORMAT_DATE);
		} else { // 秒数
			d = DateFormatUtils.format(Long.valueOf(time * 1000),
					DateFormatUtils.FORMAT_DATE);
		}
		return d;
	}

	/**
	 * 根据出生日期获取人的年龄
	 * 
	 * @param strBirthDate
	 *            (yyyy-mm-dd or yyyy/mm/dd)
	 * @return
	 */
	public static String getPersonAgeByBirthDate(Date dateBirthDate) {
		if (dateBirthDate == null) {
			return "";
		}
		String strBirthDate = DateFormatUtils.formatDate(dateBirthDate,
				DateFormatUtils.FORMAT_DATE);
		// 读取当前日期
		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH) + 1;
		int day = c.get(Calendar.DATE);
		// 计算年龄
		int age = year - Integer.parseInt(strBirthDate.substring(0, 4)) - 1;
		if (Integer.parseInt(strBirthDate.substring(5, 7)) < month) {
			age++;
		} else if (Integer.parseInt(strBirthDate.substring(5, 7)) == month
				&& Integer.parseInt(strBirthDate.substring(8, 10)) <= day) {
			age++;
		}
		return String.valueOf(age);
	}

	/**
	 * 根据出生日期获取人的年龄
	 * 
	 * @param strBirthDate
	 *            (yyyy-mm-dd or yyyy/mm/dd)
	 * @return
	 * @throws ParseException
	 */
	public static String getPersonAgeByBirthDate(String strBirthDate)
			throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(DateFormatUtils.FORMAT_DATE);
		if (StringUtils.isBlank(strBirthDate))
			return "";
		try {
			strBirthDate = sdf.format(sdf.parse(strBirthDate));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			sdf = new SimpleDateFormat(DateFormatUtils.FORMAT_MONTH);
			strBirthDate = sdf.format(sdf.parse(strBirthDate)) + "01日";
		}
		// 读取当前日期
		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH) + 1;
		int day = c.get(Calendar.DATE);
		// 计算年龄
		int age = year - Integer.parseInt(strBirthDate.substring(0, 4)) - 1;
		if (Integer.parseInt(strBirthDate.substring(5, 7)) < month) {
			age++;
		} else if (Integer.parseInt(strBirthDate.substring(5, 7)) == month
				&& Integer.parseInt(strBirthDate.substring(8, 10)) <= day) {
			age++;
		}
		if (age == 0)
			age++;
		return String.valueOf(age);
	}
	
	/**
	 * @return 当天剩余秒数
	 */
	public static int getRestMiao(){
		Calendar curDate = Calendar.getInstance();
		Calendar tommorowDate = new GregorianCalendar(curDate
				.get(Calendar.YEAR), curDate.get(Calendar.MONTH), curDate
				.get(Calendar.DATE) + 1, 0, 0, 0);
		return (int)(tommorowDate.getTimeInMillis() - curDate .getTimeInMillis()) / 1000;
	}
}
