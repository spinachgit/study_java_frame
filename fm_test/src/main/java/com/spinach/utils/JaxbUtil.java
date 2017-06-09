package com.spinach.utils;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/**
 * <p>
 * xml和bean转换工具类
 * </p>
 * 
 * @version 1.0
 * @author douzhongfeng
 * @date 2014年11月18日
 */
public class JaxbUtil {

	/**
	 * <p>
	 * 将bean转换为xml字符串
	 * </p>
	 * 
	 * @param obj
	 * @param flag 是否要去掉<?xml version="1.0" encoding="UTF-8" standalone="yes"?>声明
	 * @return
	 * @throws JAXBException
	 */
	public static String beanToXml(Object obj, boolean flag) throws JAXBException {
		JAXBContext context = JAXBContext.newInstance(obj.getClass());
		Marshaller m = context.createMarshaller();
		StringWriter sw = new StringWriter();
		m.marshal(obj, sw);
		String result = sw.toString();
		if (flag) {
			int p = result.indexOf("?>") + 2;
			result = result.substring(p);
		}
		return result;
	}

	/**
	 * <p>
	 * 将xml转换为bean
	 * </p>
	 * 
	 * @param cls
	 * @return
	 * @throws Exception
	 */
	public static Object xmlToBean(Class<?> cls, String xml) throws Exception {
		if (xml.indexOf("<?xml") == -1) {
			xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + xml;
		}
		Object obj = cls.newInstance();
		JAXBContext context = JAXBContext.newInstance(cls);
		Unmarshaller um = context.createUnmarshaller();
		StringReader sr = new StringReader(xml);
		obj = um.unmarshal(sr);
		return obj;
	}

	/**
	 * <p>
	 * 取内容（去除头和尾）加上xml声明头
	 * </p>
	 * 
	 * @param header
	 * @param tail
	 * @param content
	 * @return
	 */
	public static String getBodyAfterRemoveHeader(String header, String tail, String content) {
		int startPos = content.indexOf(header);
		int endPos = content.indexOf(tail);
		String body = content.substring(startPos + header.length(), endPos);
		if (body.indexOf("<?xml") == -1) {
			body = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + body;
		}
		return body;
	}
}
