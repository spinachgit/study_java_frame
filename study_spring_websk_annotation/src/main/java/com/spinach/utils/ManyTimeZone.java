package com.spinach.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import java.util.Vector;

public class ManyTimeZone {
	// 默认用户时区编号北京
	public static final String DEFAULT_TIMEZONE = "Asia/Shanghai";
	public static final String DATETIME_FORMATER = "yyyy-MM-dd HH:mm:ss";
	public static final String DATE_FORMATER = "yyyy-MM-dd";
	public static final String TIME_FORMATER = "HH:mm:ss";
	public static final String DATE_TIME_HOUR_FORMATER = "yyyy-MM-dd HH:mm";

	/**
	 * 对日期(时间)中有field参数指定的日期成员进行加减计算
	 * 
	 * @param d
	 * @param field
	 * @param amount
	 * @return
	 */
	public static Date calculate(Date d, int field, int amount) {
		if (d == null) {
			return null;
		}
		GregorianCalendar g = new GregorianCalendar();
		g.setGregorianChange(d);
		g.add(field, amount);
		return g.getTime();
	}

	/**
	 * 对日期(时间)中的日进行加减计算
	 * 
	 * @param d
	 * @param amount
	 * @return
	 */
	public static Date calculateByDate(Date d, int amount) {
		return calculate(d, GregorianCalendar.DATE, amount);
	}

	public static Date calculateByMinute(Date d, int amount) {
		return calculate(d, GregorianCalendar.MINUTE, amount);
	}

	public static Date calculateByYear(Date d, int amount) {
		return calculate(d, GregorianCalendar.YEAR, amount);
	}

	/**
	 * 日期(时间)转化为字符串
	 * 
	 * @param formater 日期时间格式
	 * @param aDate Date类的实例
	 * @return 日期转化后的字符串
	 */
	public static String date2String(String formater, Date aDate) {
		if (formater == null || "".equals(formater))
			return null;
		if (aDate == null)
			return null;
		return (new SimpleDateFormat(formater).format(aDate));
	}

	/**
	 * 当前日期转化为字符串
	 * 
	 * @param formater 日期时间格式
	 * @return 日期转化后的字符串
	 */
	public static String date2String(String formater) {
		return date2String(formater, new Date());
	}

	/**
	 * 获取当前日期对应的星期数
	 * 
	 * @return
	 */
	public static int dayOfWeek() {
		GregorianCalendar g = new GregorianCalendar();
		int ret = g.get(java.util.Calendar.DAY_OF_WEEK);
		g = null;
		return ret;
	}

	/**
	 * 获取所有时区编号
	 * 
	 * @return
	 */
	public static String[] fecthAllTimeZoneIds() {
		Vector<String> v = new Vector<String>();
		String[] ids = TimeZone.getAvailableIDs();
		for (int i = 0; i < ids.length; i++) {
			v.add(ids[i]);
		}
		java.util.Collections.sort(v, String.CASE_INSENSITIVE_ORDER);
		v.copyInto(ids);
		v = null;
		return ids;
	}

	/**
	 * 将日期时间字符串根据转换为指定时区的日期时间
	 * 
	 * @param srcFormater 待转换的日期时间格式
	 * @param srcDateTime 待转换的日期时间
	 * @param dstFormater 目标的日期时间格式
	 * @param dstTimeZoneld 目标时区编号
	 * @return
	 */
	public static String string2Timezone(String srcFormater, String srcDateTime, String dstFormater, String dstTimeZoneld) {
		if (srcFormater == null || "".equals(srcFormater))
			return null;
		if (srcDateTime == null || "".equals(srcDateTime))
			return null;
		if (dstFormater == null || "".equals(dstFormater))
			return null;
		if (dstTimeZoneld == null || "".equals(dstTimeZoneld))
			return null;
		SimpleDateFormat sdf = new SimpleDateFormat(srcFormater);
		try {
			int diffTime = getDiffTimeZoneRawOffset(dstTimeZoneld);
			Date d = sdf.parse(srcDateTime);
			long nowTime = d.getTime();
			long newNowTime = nowTime - diffTime;
			d = new Date(newNowTime);
			return date2String(dstFormater, d);
		} catch (Exception e) {
			return null;
		} finally {
			sdf = null;
		}
	}

	/**
	 * 当地时间转换为北京时间
	 * 
	 * @param srcFormater
	 * @param srcDateTime
	 * @param dstFormater
	 * @param dstTimeZoneld
	 * @return
	 */
	public static String string2TimezoneToBj(String srcFormater, String srcDateTime, String dstFormater, String dstTimeZoneld) {
		if (srcFormater == null || "".equals(srcFormater))
			return null;
		if (srcDateTime == null || "".equals(srcDateTime))
			return null;
		if (dstFormater == null || "".equals(dstFormater))
			return null;
		SimpleDateFormat sdf = new SimpleDateFormat(srcFormater);
		try {
			int diffTime = getDiffTimeZoneRawOffsetByBj(dstTimeZoneld);
			Date d = sdf.parse(srcDateTime);
			long nowTime = d.getTime();
			long newNowTime = nowTime - diffTime;
			d = new Date(newNowTime);
			return date2String(dstFormater, d);
		} catch (Exception e) {
			return null;
		} finally {
			sdf = null;
		}
	}

	/**
	 * 获取系统默认时区与UTC的时间差
	 */
	private static int getDefaultTimeZoneRawOffset() {
		return TimeZone.getDefault().getRawOffset();
	}

	/**
	 * 获取指定时区与UTC的时间差
	 */
	private static int getTimeZoneRawOffset(String timeZoneId) {
		return TimeZone.getTimeZone(timeZoneId).getRawOffset();
	}

	/**
	 * 获取系统当前默认时区与指定时区的时间差
	 * 
	 * @param timeZoneId
	 * @return
	 */
	private static int getDiffTimeZoneRawOffset(String timeZoneId) {
		// return TimeZone.getDefault().getRawOffset() - TimeZone.getTimeZone(timeZoneId).getRawOffset();
		return TimeZone.getTimeZone(DEFAULT_TIMEZONE).getRawOffset() - TimeZone.getTimeZone(timeZoneId).getRawOffset();
	}

	/**
	 * 北京时间与当地时间的时间差
	 */
	private static int getDiffTimeZoneRawOffsetByBj(String timeZoneId) {
		return TimeZone.getTimeZone(timeZoneId).getRawOffset() - TimeZone.getTimeZone(DEFAULT_TIMEZONE).getRawOffset();
	}

	/**
	 * 将日期字符串转换为指定时区的日期时间
	 * 
	 * @param srcDateTime
	 * @param dstTimeZoneId
	 * @param type 1日期格式 2时间格式 3 日期时间格式(时分秒) 0日期时间格式(时分) 默认0
	 * @return
	 */
	public static String string2TimezoneDefault(String srcDateTime, String dstTimeZoneId, int type) {
		if (1 == type) {
			return string2Timezone(DATE_FORMATER, srcDateTime, DATE_FORMATER, dstTimeZoneId);
		} else if (2 == type) {
			return string2Timezone(TIME_FORMATER, srcDateTime, TIME_FORMATER, dstTimeZoneId);
		} else if (3 == type) {
			return string2Timezone(DATETIME_FORMATER, srcDateTime, DATETIME_FORMATER, dstTimeZoneId);
		} else {
			return string2Timezone(DATE_TIME_HOUR_FORMATER, srcDateTime, DATE_TIME_HOUR_FORMATER, dstTimeZoneId);
		}

	}

	private void test(Object o) {

	}
}
