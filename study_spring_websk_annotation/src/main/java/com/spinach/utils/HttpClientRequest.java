package com.spinach.utils;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpClientRequest {

	private final static Logger logger = LoggerFactory.getLogger(HttpClientRequest.class);

	private static int getPage(int page) {
		return page == 0 ? 1 : page + 1;
	}

	/**
	 * 浦东机场提供数据<航班综合信息>
	 * 
	 * @param page 页数
	 * @return
	 * @throws Exception
	 */
	public static Map<String, String> getPvgMap(int page) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("xml",
				"<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:pvg=\"http://pvg.unified.ws.foc.spring.com/\"><soapenv:Header/><soapenv:Body><pvg:pvgAirportMsgInfo><arg0>"
						+ getPage(page) + "</arg0><arg1>fm</arg1></pvg:pvgAirportMsgInfo></soapenv:Body></soapenv:Envelope>");
		map.put("path", ResourceUtils.getPropertyInSystem("query.msg.url") + "/pvgWebservice?wsdl");
		return map;
	}

	/**
	 * 虹桥机场提供数据<航班动态信息>
	 * 
	 * @param page 页数
	 * @return
	 * @throws Exception
	 */
	public static Map<String, String> getSha_F_Map(int page) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("xml",
				"<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:sha=\"http://sha.unified.ws.foc.spring.com/\"><soapenv:Header/><soapenv:Body><sha:shaAirportFltInfoMsgInfo><arg0>"
						+ getPage(page) + "</arg0><arg1>fm</arg1></sha:shaAirportFltInfoMsgInfo></soapenv:Body></soapenv:Envelope>");
		map.put("path", ResourceUtils.getPropertyInSystem("query.msg.url") + "/shaWebservice?wsdl");
		return map;
	}

	/**
	 * 虹桥机场提供数据<航班资源信息>
	 * 
	 * @param page 页数
	 * @return
	 * @throws Exception
	 */
	public static Map<String, String> getSha_R_Map(int page) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("xml",
				"<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:sha=\"http://sha.unified.ws.foc.spring.com/\"><soapenv:Header/><soapenv:Body><sha:shaAirportRescInfoMsgInfo><arg0>"
						+ getPage(page) + "</arg0><arg1>fm</arg1></sha:shaAirportRescInfoMsgInfo></soapenv:Body></soapenv:Envelope>");
		map.put("path", ResourceUtils.getPropertyInSystem("query.msg.url") + "/shaWebservice?wsdl");
		return map;
	}

	/**
	 * webservice 接口调用获取数据
	 */
	public static String getWebService(Map<String, String> map) {
		logger.info("调用接口开始............");
		String str = "";
		try {
			HttpURLConnection connection = null;
			OutputStream outputStream = null;
			URL url = null;
			InputStreamReader reader = null;

			StringBuilder builer = new StringBuilder();
			builer.append(map.get("xml"));
			logger.info("调用机场数据接口xml:" + map.get("xml"));
			StringBuilder bus = new StringBuilder();
			logger.info("调用机场数据接口:" + map.get("path"));
			bus.append(map.get("path"));

			url = new URL(bus.toString());
			connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			try {
				outputStream = connection.getOutputStream();
			} catch (Exception e) {
				logger.error("[定时获取机场信息失败]" + "时间:" + DateUtil.getCurDate("yyyy-MM-dd HH:mm:ss") + ",错误信息:" + e.getMessage());
				e.printStackTrace();
			}

			byte[] bytes = builer.toString().getBytes();
			outputStream.write(bytes);
			outputStream.flush();
			InputStream input = connection.getInputStream();
			reader = new InputStreamReader(input);
			char charset[] = new char[1];
			StringBuffer result = new StringBuffer();
			while (reader.read(charset) != -1) {
				result.append(new String(charset));
			}
			str = result.toString().substring(result.toString().indexOf("<data>") + "<data>".length(), result.toString().lastIndexOf("</data>"));
		} catch (Exception e) {
			logger.error("[机场数据接口返回数据:" + str + "] " + DateUtil.getCurDate("yyyy-MM-dd HH:mm:ss") + " 错误信息:" + e.getMessage());
			str = "";
			e.printStackTrace();
		}
		logger.info("调用接口结束............");
		return str;
	}

	/**
	 * webservice 接口调用获取数据
	 * 
	 * @param map
	 * @param interfaceName 接口名称
	 * @return
	 */
	public static String getWebServiceInfo(Map<String, String> map, String interfaceName) {
		logger.info("调用" + interfaceName + "接口开始............" + "时间:" + DateUtil.getCurDate("yyyy-MM-dd HH:mm:ss"));
		String str = "";
		try {
			HttpURLConnection connection = null;
			OutputStream outputStream = null;
			URL url = null;
			InputStreamReader reader = null;

			StringBuilder builer = new StringBuilder();
			builer.append(map.get("xml"));
			logger.info("调用数据接口xml:" + map.get("xml"));
			StringBuilder bus = new StringBuilder();
			logger.info("调用数据接口path:" + map.get("path"));
			bus.append(map.get("path"));

			url = new URL(bus.toString());
			connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			try {
				outputStream = connection.getOutputStream();
			} catch (Exception e) {
				logger.error("[定时获取" + interfaceName + "数据接口]" + "时间:" + DateUtil.getCurDate("yyyy-MM-dd HH:mm:ss") + ",错误信息:" + e.getMessage());
			}

			byte[] bytes = builer.toString().getBytes();
			outputStream.write(bytes);
			outputStream.flush();
			InputStream input = connection.getInputStream();
			reader = new InputStreamReader(input, "utf-8");
			char charset[] = new char[1];
			StringBuffer result = new StringBuffer();
			while (reader.read(charset) != -1) {
				result.append(new String(charset));
			}
			str = result.toString().substring(result.toString().indexOf("<return>") + "<return>".length(), result.toString().lastIndexOf("</return>"));
		} catch (Exception e) {
			logger.error("[" + interfaceName + "返回数据:" + str + "] " + DateUtil.getCurDate("yyyy-MM-dd HH:mm:ss") + " 错误信息:" + e.getMessage());
			str = "";
		}
		logger.info("调用" + interfaceName + "接口结束............" + "时间:" + DateUtil.getCurDate("yyyy-MM-dd HH:mm:ss"));
		return str;
	}
}
