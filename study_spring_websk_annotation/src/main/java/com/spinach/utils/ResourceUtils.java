package com.spinach.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.springframework.core.io.ClassPathResource;

public class ResourceUtils {

	public static final String SYSTEM_PROPERTIES = "system.properties";
	private static Map<String, String> propMap = new HashMap<String, String>();

	public static String getPropertyInSystem(String key) throws Exception {
		ClassPathResource resouce = new ClassPathResource("conf/system" + Constants.FM_FILE_SEP + SYSTEM_PROPERTIES);

		Properties property = new Properties();
		FileInputStream in = null;
		String serviceUrl = null;
		if (propMap.containsKey(key)) {
			serviceUrl = propMap.get(key);
		} else {
			try {
				in = new FileInputStream(resouce.getFile());
				property.load(in);
				serviceUrl = (String) property.get(key);
				propMap.put(key, serviceUrl);
				if (StringUtils.isBlank(serviceUrl)) {
					throw new Exception("properties error");
				}
			} catch (FileNotFoundException e) {
				throw new Exception("propertie file not found", e);
			} finally {
				try {
					if (in != null) {
						in.close();
						in = null;
					}
				} catch (Exception e) {

				}
			}
		}
		return serviceUrl;
	}

	public static void main(String[] args) throws Exception {

	}
}
