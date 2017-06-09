package com.spinach.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class DateUtil {

	private static DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

	/**
	 * 一般情况下，各月份天数最大值
	 */
	private static int MAX_DAY[] = { 0, 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

	public static void main(String[] args) throws Exception {

	}

	/**
	 * 取得当天的时间
	 * 
	 * @return 当天的时间
	 */
	public static Date getToday() {
		Calendar cal = Calendar.getInstance();
		return cal.getTime();
	}
	/**
	 * 获取明天的日期字符串
	 */
	public static String getTomorrow() {
		Date tomorrowDate = new Date();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(tomorrowDate);
		calendar.add(Calendar.DATE, 1);
		tomorrowDate = calendar.getTime();
		SimpleDateFormat formtime =new SimpleDateFormat("yyyy-MM-dd");
		return formtime.format(tomorrowDate);
	}
	

	/**
	 * 取得当天的时间，格式为yyyyMMdd
	 * 
	 * @return
	 */
	public static String getCurDate() {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		format.setLenient(false);
		return format.format(new Date());
	}

	/**
	 * <p>
	 * 取得当前时间。
	 * </p>
	 * 
	 * @param df
	 * @return
	 */
	public static String getCurDate(String df) {
		SimpleDateFormat format = new SimpleDateFormat(df);
		format.setLenient(false);
		return format.format(new Date());
	}


	/**
	 * 获取两个日期的天数
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */

	public static int compareDate(Date date1, Date date2) {
		return (int) ((date2.getTime() - date1.getTime()) / (24 * 60 * 60 * 1000));
	}

	/**
	 * 传入参数为毫秒数，计算出的时间为：某日某时某分某秒
	 * 
	 * @param minus
	 * @return
	 */
	public static String calMius(long minus) {
		StringBuilder sb = new StringBuilder();

		if (minus <= 0)
			return "00 00:00:00";
		// 日
		long day = minus / (24 * 60 * 60 * 1000);
		if (day < 10)
			sb.append("0");
		sb.append(day);
		sb.append(" ");
		// 时
		long hour = minus / (60 * 60 * 1000) - day * 24;
		if (hour < 10)
			sb.append("0");
		sb.append(hour);
		sb.append(":");
		// 分
		long minute = minus / (60 * 1000) - day * 24 * 60 - hour * 60;
		if (minute < 10)
			sb.append("0");
		sb.append(minute);
		sb.append(":");
		// 秒
		long second = minus / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - minute * 60;
		if (second < 10)
			sb.append("0");
		sb.append(second);

		return sb.toString();

	}

	/**
	 * 清空date中的时分秒
	 * 
	 * @param date
	 * @return 
	 */
	public static Date truncateTimeOfDate(Date date) {
		try {
			df.parse(df.format(date));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 获取一个月的第一天
	 * 
	 * @param date
	 */
	public static void firstOfMonth(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(truncateTimeOfDate(date));
		c.set(Calendar.DAY_OF_MONTH, 1);
	}

	/**
	 * 获取一个月的最后一天
	 * 
	 * @param date
	 */
	public static void lastOfMonth(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(truncateTimeOfDate(date));
		c.add(Calendar.MONTH, 1);
		c.set(Calendar.DAY_OF_MONTH, 1);
		c.add(Calendar.DAY_OF_MONTH, -1);
	}

	/**
	 * 校验时间格式是否正确，若格式正确则返回true，否则返回false
	 * 
	 * @param dateStr
	 * @param formart 可为:yyyy-MM-dd HH:mm:ss,yyyy-MM-dd,yyyy-MM-dd HH:mm,yyyyMMddHHmmss等等
	 * @return boolean
	 */
	public static boolean checkStrFormat(String dateStr, String formart) {
		try {
			DateFormat df = new SimpleDateFormat(formart);
			Date day = df.parse(dateStr);

			return day != null;

		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 计算时间差
	 * 
	 * @param sdate 结束时间
	 * @param edate 开始时间
	 * @param df 日期格式
	 * @return
	 */
	public static long minDiffer(String sdate, String edate, String df) {
		long mins = 0L;

		SimpleDateFormat format = new SimpleDateFormat(df);
		Date d1 = new Date();
		Date d2 = new Date();
		try {
			d1 = format.parse(edate);
			d2 = format.parse(sdate);
			long diff = d1.getTime() - d2.getTime();
			mins = diff / (1000 * 60);
		} catch (ParseException e) {
			//throw new Exception("ERROR.COMMON.001", new Object[] { sdate + "&" + edate });
			
		}

		return mins;
	}

	/**
	 * 计算时间差
	 * 
	 * @param sdate
	 * @param edate
	 * @return
	 * @throws ParseException
	 */
	public static long minDiffer(String sdate, String edate) {
		SimpleDateFormat format = new SimpleDateFormat("HH:mm");
		Date d1 = new Date();
		Date d2 = new Date();

		long mins = 0;
		try {
			d1 = format.parse(edate);
			d2 = format.parse(sdate);
			long diff = d1.getTime() - d2.getTime();
			mins = diff / (1000 * 60);
		} catch (Exception e) {
			//throw new BusinessException("ERROR.COMMON.001", new Object[] { sdate + "&" + edate });
		}
		return mins;
	}

	/**
	 * 计算时间差
	 * 
	 * @param sdate
	 * @param edate
	 * @return
	 * @throws ParseException
	 */
	public static long minDiffer(Date sdate, Date edate) {
		long mins = 0;
		try {
			if (sdate == null)
				sdate = Calendar.getInstance().getTime();
			if (edate == null)
				edate = Calendar.getInstance().getTime();

			long diff = sdate.getTime() - edate.getTime();
			mins = diff / (1000 * 60);
		} catch (Exception e) {
			//throw new BusinessException("ERROR.COMMON.001", new Object[] { sdate + "&" + edate });
		}
		return mins;
	}

	/**
	 * 判断输入的日期和时间是否有效
	 * 
	 * @param year
	 * @param month
	 * @param day
	 * @param hour
	 * @param minute
	 * @return
	 */
	public static boolean isValidDate(String year, String month, String day, String hour, String minute) {
		if (isValidDate(year, month, day) == false) {
			return false;
		}
		int hourValue;
		int minuteValue;
		try {
			hourValue = Integer.parseInt(hour);
			minuteValue = Integer.parseInt(minute);
		} catch (NumberFormatException e) {
			return false;
		}
		if (hourValue > 23 || hourValue < 0) {
			return false;
		}
		if (minuteValue >= 60 || minuteValue < 0) {
			return false;
		}
		return true;
	}

	/**
	 * 判断是否合法的日期
	 * 
	 * @param year 年
	 * @param month 月
	 * @param day 日
	 * @return boolean true:有效 false:无效
	 */
	public static boolean isValidDate(String year, String month, String day) {

		int yearValue;
		int monthValue;
		int dayValue;
		try {
			yearValue = Integer.parseInt(year);
			monthValue = Integer.parseInt(month);
			dayValue = Integer.parseInt(day);
		} catch (NumberFormatException e) {
			return false;
		}

		// 月检查
		if (!isValidMonth(month))
			return false;
		// 闰年2月29天
		if ((monthValue == 2) && (isLeapYear(yearValue))) {
			if (dayValue > 29)
				return false;
		}
		// 平年2月28天
		if ((monthValue == 2) && (!isLeapYear(yearValue))) {
			if (dayValue > 28)
				return false;
		}
		// 超过各月正常的最大值(2月另外判断)
		if (dayValue > MAX_DAY[monthValue])
			return false;

		// 没有满一天的情况
		if (1 > dayValue)
			return false;

		return true;
	}

	/**
	 * 判断输入的月份是否有效
	 * 
	 * @param month 月
	 * @return true 不是返回false
	 */
	public static boolean isValidMonth(String month) {
		// 在1月到12月之间
		int monthValue;
		try {
			monthValue = Integer.parseInt(month);
		} catch (NumberFormatException e) {
			return false;
		}
		if ((monthValue < 1) || (monthValue > 12))
			return false;
		return true;
	}

	/**
	 * 判读是否闰年
	 * 
	 * @param year 年
	 * @return true 不是返回false
	 */
	public static boolean isLeapYear(int year) {
		boolean leapYear = false;
		if ((year % 400 == 0) || ((year % 4 == 0) && (year % 100 != 0)))
			leapYear = true;
		return leapYear;
	}

	/**
	 * 比较二个日期大小
	 * 
	 * @param startDate 开始日期java.util.Date
	 * @param endDate 结束日期java.util.Date
	 * @return Integer 两个日期的大小; 有一个为空就返回null； startDate>endDate 返回1；startDate=endDate 返回0；startDate<endDate 返回-1
	 */
	public static Integer dateTimeEquals(Date startDate, Date endDate) {
		if (startDate == null || endDate == null) {
			return null;
		}

		if (startDate.getTime() > endDate.getTime()) {
			return 1;
		} else if (startDate.getTime() == endDate.getTime()) {
			return 0;
		} else {
			return -1;
		}

	}

	/**
	 * 字符串转为date
	 * 
	 * @param dateStr
	 * @return
	 */
	public static Date stringToDate(String dateStr) {
		SimpleDateFormat tempDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date datetime;
		try {
			datetime = tempDate.parse(dateStr);
		} catch (ParseException e) {
			datetime = null;
		}
		return datetime;
	}

	/**
	 * 字符串转为date
	 * 
	 * @param dateStr
	 * @param format 格式
	 * @return
	 */
	public static Date stringToDate(String dateStr, String format) {
		SimpleDateFormat tempDate = new SimpleDateFormat(format);
		Date datetime;
		try {
			datetime = tempDate.parse(dateStr);
		} catch (ParseException e) {
			datetime = null;
		}
		return datetime;
	}

	/**
	 * <p>
	 * 字符串转为date<br>
	 * yyyy-MM-dd HH:mm:ss
	 * </p>
	 * 
	 * @param date
	 * @return
	 */
	public static String datesToString(Date date) {
		SimpleDateFormat tempDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String datetimeStr;
		datetimeStr = tempDate.format(date);
		return datetimeStr;
	}

	/**
	 * <p>
	 * 字符串转为date<br>
	 * </p>
	 * 
	 * @param date
	 * @param format 格式
	 * @return
	 */
	public static String datesToString(Date date, String format) {
		SimpleDateFormat tempDate = new SimpleDateFormat(format);
		String datetimeStr;
		datetimeStr = tempDate.format(date);
		return datetimeStr;
	}

	public static Date addDay(Object dt, long day) {
		return addMilliSecond(dt, 1000L * 60L * 60L * 24L * day);
	}

	// Add millisecond
	public static Date addMilliSecond(Object dt, long millisecond) {
		Date tmp = convertDateObject(dt);
		tmp.setTime(tmp.getTime() + millisecond);
		return tmp;
	}

	/**
	 * @param dt
	 * @return
	 */
	public static java.util.Date convertDateObject(Object dt) {
		if (dt == null) {
			return null;
		}
		if (!(dt instanceof String) && !(dt instanceof java.sql.Date) && !(dt instanceof java.util.Date) && !(dt instanceof java.lang.Integer)
				&& !(dt instanceof java.lang.Long))
			throw new IllegalArgumentException("日期格式必须是String、Date、Long、Integer类型,请正确输入!");
		Date result = null;
		if (dt instanceof String)
			result = parseString((String) dt);
		else if (dt instanceof java.util.Date)
			result = new java.util.Date(((java.util.Date) dt).getTime());
		else if (dt instanceof java.sql.Date)
			result = new java.util.Date(((java.sql.Date) dt).getTime());
		else
			result = parseString(dt.toString());
		return result;
	}

	/**
	 * @param dateStr
	 * @return
	 */
	public static Date parseString(String dateStr) {
		if (dateStr == null)
			return null;
		dateStr = dateStr.trim();
		if (dateStr.equals(""))
			return null;
		DateFormat df;
		try {
			if (matchs(dateStr, "^\\d{1,2}\\:\\d{1,2}\\:\\d{1,2}$"))
				df = new SimpleDateFormat("HH:mm:ss");
			else if (matchs(dateStr, "^\\d{1,2}\\:\\d{1,2}\\:\\d{1,2}\\.\\d{1,3}$"))
				df = new SimpleDateFormat("HH:mm:ss.SSS");
			else if (matchs(dateStr, "^\\d{1,2}\\.\\d{1,2}\\.\\d{1,2}$"))
				df = new SimpleDateFormat("HH.mm.ss");
			else if (matchs(dateStr, "^\\d{1,2}\\.\\d{1,2}$"))
				df = new SimpleDateFormat("HH.mm");
			else if (matchs(dateStr, "^\\d{1,2}\\:\\d{1,2}$"))
				df = new SimpleDateFormat("HH:mm");
			else {
				dateStr = dateStr.replace("-", "/").replace(".", "/").replace(":", "/").replace("\\", "/");
				if (matchs(dateStr, "^\\d{4}\\/\\d{1,2}\\/\\d{1,2}$"))
					df = new SimpleDateFormat("yyyy/MM/dd");
				else if (matchs(dateStr, "^\\d{4}\\/\\d{1,2}\\/\\d{1,2}\\s+\\d{1,2}\\/\\d{1,2}\\/\\d{1,2}$"))
					df = new SimpleDateFormat("yyyy/MM/dd HH/mm/ss");
				else if (matchs(dateStr, "^\\d{8}$"))
					df = new SimpleDateFormat("yyyyMMdd");
				else if (matchs(dateStr, "^\\d{8}\\s+\\d{6}$"))
					df = new SimpleDateFormat("yyyyMMdd HHmmss");
				else if (matchs(dateStr, "^\\d{4}\\/\\d{1,2}$"))
					df = new SimpleDateFormat("yyyy/MM");
				else if (matchs(dateStr, "^\\d{6}$"))
					df = new SimpleDateFormat("yyyyMM");
				else if (matchs(dateStr, "^\\d{4}\\/\\d{1,2}\\/\\d{1,2}\\s+\\d{1,2}\\/\\d{1,2}\\/\\d{1,2}\\/\\d{1,3}$"))
					df = new SimpleDateFormat("yyyy/MM/dd HH/mm/ss/SSS");
				else if (matchs(dateStr, "^\\d{2}\\/\\d{1,2}\\/\\d{1,2}$"))
					df = new SimpleDateFormat("yy/MM/dd");
				else if (matchs(dateStr, "^\\d{2}\\/\\d{2}$"))
					df = new SimpleDateFormat("yy/MM");
				else if (matchs(dateStr, "^\\d{2}\\/\\d{1,2}\\/\\d{1,2}\\s+\\d{1,2}\\/\\d{1,2}\\/\\d{1,2}$"))
					df = new SimpleDateFormat("yy/MM/dd HH/mm/ss");
				else if (matchs(dateStr, "^\\d{2}\\/\\d{1,2}\\/\\d{1,2}\\s+\\d{1,2}\\/\\d{1,2}$"))
					df = new SimpleDateFormat("yy/MM/dd HH/mm");
				else if (matchs(dateStr, "^\\d{4}\\/\\d{1,2}\\/\\d{1,2}\\/\\d{1,2}\\/\\d{1,2}\\/\\d{1,2}$"))
					df = new SimpleDateFormat("yyyy/MM/dd/HH/mm/ss");
				else if (matchs(dateStr, "^\\d{4}\\/\\d{1,2}\\/\\d{1,2}\\/\\d{1,2}\\/\\d{1,2}\\/\\d{1,2}\\/\\d{1,3}$"))
					df = new SimpleDateFormat("yyyy/MM/dd/HH/mm/ss/SSS");
				else if (matchs(dateStr, "^\\d{2}\\/\\d{1,2}\\/\\d{1,2}\\/\\d{1,2}\\/\\d{1,2}\\/\\d{1,2}$"))
					df = new SimpleDateFormat("yy/MM/dd/HH/mm/ss");
				else if (matchs(dateStr, "^\\d{2}\\/\\d{1,2}\\/\\d{1,2}\\/\\d{1,2}\\/\\d{1,2}$"))
					df = new SimpleDateFormat("yy/MM/dd/HH/mm");
				else if (matchs(dateStr, "^\\d{6}\\s+\\d{6}$"))
					df = new SimpleDateFormat("yyMMdd HHmmss");
				else if (matchs(dateStr, "^\\d{7,9}$"))
					df = new SimpleDateFormat("HHmmssSSS");
				else if (matchs(dateStr, "^\\d{14}$"))
					df = new SimpleDateFormat("yyyyMMddHHmmss");
				else if (matchs(dateStr, "^\\d{15,17}$"))
					df = new SimpleDateFormat("yyyyMMddHHmmssSSS");
				else if (matchs(dateStr, "^\\d{12}$"))
					df = new SimpleDateFormat("yyMMddHHmmss");
				else if (matchs(dateStr, "^\\d{4}$"))
					df = new SimpleDateFormat("yyyy");
				else
					df = new SimpleDateFormat("yyyy/MM/dd");
			}
			return df.parse(dateStr);
		} catch (java.text.ParseException e) {
			// e.printStackTrace();
		}
		return null;
	}

	/**
	 * @param source
	 * @param regex
	 * @return
	 */
	public static boolean matchs(String source, String regex) {
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(source);
		return m.find();
	}

	/**
	 * @param str
	 * @return Date
	 */
	public static Date parseString(String dateString, String format) {
		if (dateString == null || "".equals(dateString) || "null".equals(dateString.toLowerCase())) {

			return null;
		}
		try {
			if (isNullOrBlank(format))
				return parseString(dateString);
			DateFormat df = new SimpleDateFormat(format);
			return df.parse(dateString);
		} catch (Exception e) {
			// e.printStackTrace();
			return null;
		}
	}

	public static boolean isNullOrBlank(String str) {
		if (str == null || str.trim().equals("") || str.equalsIgnoreCase("null")) {
			return true;
		} else {
			return false;
		}
	}
}
