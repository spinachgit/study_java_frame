package com.spinach.utils;

import java.lang.reflect.Field;

import com.fasterxml.jackson.databind.util.BeanUtil;

public class BeanUtils {

	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(BeanUtil.class);

	/**
	 * 复制bean2中的属性给bean1
	 * 
	 * @param bean1
	 * @param bean2
	 */
	public static void copy(Object bean1, Object bean2) {
		Field[] fields2 = bean2.getClass().getDeclaredFields();

		for (Field field2 : fields2) {
			field2.setAccessible(true);
			try {
				Field field1 = bean1.getClass().getDeclaredField(field2.getName());
				field1.setAccessible(true);
				field1.set(bean1, field2.get(bean2));
			} catch (Exception e) {
				logger.info(field2.getName() + "该属性在" + bean1.getClass().getSimpleName() + "中没有找到，所以跳过改属性复制");
			}

		}

	}

}
