package com.spinach.utils;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommonTool {
	private final static Logger logger = LoggerFactory.getLogger(CommonTool.class);

	public static String coverNull(String s) {
		return s != null && s.length() != 0 ? s : "";
	}

	public static String obj2String(Object obj) {
		return obj == null ? "" : obj.toString();
	}

	public static String objToString(Object obj) {
		return obj == null ? "0" : obj.toString();
	}

	/**
	 * 验证输入的邮箱格式是否合法
	 * 
	 * @param email
	 * @return 是否合法
	 */
	public static boolean isEmailFormat(String email) {
		boolean tag = true;
		String pattern = "^[a-z0-9A-Z]+@([a-z0-9A-Z.])+$";
		if (email != null && !"".equals(email)) {
			tag = email.trim().matches(pattern);
		} else {
			tag = false;
		}
		return tag;
	}

	public static BigDecimal obj2BigDecimal(Object obj) {
		if (obj == null || "".equals(obj)) {
			return new BigDecimal("0");
		}
		return new BigDecimal(obj.toString());
	}

	public static BigDecimal obj2BigDecimalNull(Object obj) {
		if (obj == null || "".equals(obj)) {
			return null;
		}
		return new BigDecimal(obj.toString());
	}

	public static int obj2Int(Object obj) {
		if (obj == null || "".equals(CommonTool.obj2String(obj))) {
			return 0;
		}
		if (!StringUtils.isNumeric(obj.toString())) {
			//throw new BusinessException("ERROR.COMMON.002", null);
		}
		return Integer.parseInt(obj.toString());
	}

	public static float obj2Float(Object obj) {
		if (obj == null || "".equals(CommonTool.obj2String(obj))) {
			return 0;
		}
		if (!validateNumber(obj.toString())) {
			//throw new BusinessException("ERROR.COMMON.002", null);
		}
		return Float.parseFloat(obj.toString());
	}

	public static double obj2Double(Object obj) {
		if (obj == null || "".equals(CommonTool.obj2String(obj))) {
			return 0;
		}
		if (!validateNumber(obj.toString())) {
			//throw new BusinessException("ERROR.COMMON.002", null);
		}
		return Double.parseDouble(obj.toString());
	}

	public static long obj2Long(Object obj) {
		if (obj == null || "".equals(CommonTool.obj2String(obj))) {
			return 0;
		}
		if (!validateNumber(obj.toString())) {
			//throw new BusinessException("ERROR.COMMON.002", null);
		}
		return Long.parseLong(obj.toString());
	}

	public static void deleteFolder(String dir) {
		File delfolder = new File(dir);
		File[] oldFile = delfolder.listFiles();
		try {
			for (int i = 0; i < oldFile.length; i++) {
				if (oldFile[i].isDirectory()) {
					deleteFolder(dir + oldFile[i].getName() + "//"); // 递归清空子文件夹
				}
				oldFile[i].delete();
			}
		} catch (Exception e) {
			logger.error("清空文件夹操作出错!" + e.getMessage());
		}
	}

	public static long number2Min(String s) {
		if (s == null || "".equals(s)) {
			return 0;
		}

		String hour = StringUtils.split(s, ".")[0];
		String min = StringUtils.split(s, ".")[1];

		return CommonTool.obj2Long(hour) * 60 + CommonTool.obj2Long(min);
	}

	public static String min2String(long s) {
		long min = s % 60;

		if (min < 10) {
			return (s / 60) + "." + "0" + min;
		}

		return (s / 60) + "." + min;
	}

	public static List<Object> removeDuplicateWithOrder(List<Object> list) {

		Set<Object> set = new HashSet<Object>();

		List<Object> newList = new ArrayList<Object>();

		for (Iterator<Object> iter = list.iterator(); iter.hasNext();) {

			Object element = iter.next();

			if (set.add(element))

				newList.add(element);
		}

		return newList;

	}

	public static boolean isNull(Object obj) {
		return obj == null || obj.toString().equals("") || obj.toString().toLowerCase().equals("null");
	}

	public static Boolean validateNumber(String floatString) {
		return validateFloat(floatString) || validateInt(floatString);
	}

	public static Boolean validateFloat(String floatString) {
		Pattern pattern = Pattern.compile("^-?([1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*|0?\\.0+|0)$");

		Matcher matcher = pattern.matcher(floatString); // 以验证127.400.600.2为例

		return matcher.matches();
	}

	public static boolean validateInt(String num) {
		Pattern pattern = Pattern.compile("^-?[1-9]\\d*$");
		Matcher matcher = pattern.matcher(num); // 以验证127.400.600.2为例
		return matcher.matches();
	}

	/**
	 * <p>
	 * 判断当前运行状态是否为debug模式。
	 * </p>
	 * 
	 * @return
	 */
	public static boolean isDebugMode() {
		List<String> args = ManagementFactory.getRuntimeMXBean().getInputArguments();
		boolean isDebugMode = false;
		for (String arg : args) {
			if (arg.startsWith("-agentlib:jdwp")) {
				isDebugMode = true;
				break;
			}
		}

		return isDebugMode;
	}


}
