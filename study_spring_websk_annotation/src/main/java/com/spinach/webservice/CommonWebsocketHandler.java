package com.spinach.webservice;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.spinach.domain.UserInfo;
import com.spinach.utils.CommonTool;
import com.spinach.utils.DateUtil;

import net.sf.json.JSONObject;

@Component
public class CommonWebsocketHandler{
	private static final Logger logger = LoggerFactory.getLogger(CommonWebsocketHandler.class);
	/**
	 * 取得websocket已经建立连接的 WebSocketSession对象。
	 */
	private static Map<String,WebSocketSession> clients = WebSocketSessionUtils.clients;
	
	/**
	 * <p>
	 *	根据不同的用户发送不同的消息. 
	 * </p>
	 * @author wanghuihui
	 * @date 2017-5-24下午6:29:51
	 * @param url:指定的URL
	 * @param className:类名
	 * @param methodName:方法名
	 */
	public void sendAllMessageByUser(String url,String className,String methodName) {
		if(StringUtils.isEmpty(url) || StringUtils.isEmpty(className) || StringUtils.isEmpty(methodName)){
			logger.error("发送消息开始：参数出错=== url:" + url +" == className:" + className+" == methodName:"+methodName );
			return;
		}
		try {
			//请求参数URL， 如果后期要求：兼容IE低版本的wesocket,WebsocketContants对应的SOCKJS路径
			String [] urls = {url};
			Class<?> clazz = Class.forName(className);
			Method method = clazz.getDeclaredMethod(methodName, new Class<?>[]{UserInfo.class});
			//发送消息
			sendAllMessageByUserAndUrl(urls,clazz,method);
		} catch (Exception e) {
			logger.error("发送消息给所有在线人数出错。",e);
			e.printStackTrace();
		} 
		
	}
	
	/**
	 * <p>
	 * 根据不能的用户发送不同的信息 ，面向所有用户
	 * </p>
	 * @author wanghuihui
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @date 2017-5-22下午5:11:17
	 */
	public void sendAllMessageByUserAndUrl(String[] urls, Class<?> class1, Method method) throws InstantiationException, IllegalAccessException {
		Long startTime = System.currentTimeMillis();
		logger.info("sendAllMessageByUserAndUrl开始===" + new Date());
		Object clazz = null;
		Iterator<Entry<String, WebSocketSession>> iterator = clients.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, WebSocketSession> entry = (Map.Entry<String, WebSocketSession>) iterator.next();
			WebSocketSession session = entry.getValue();
			for(String url:urls){
				//发送对应URL的消息
				System.out.println("url = "+url + "  @@@@@@@@@ session uri="+session.getUri().toString() );
				if(session.getUri().toString().endsWith(url)){
					clazz = class1.newInstance();
					Object obj = session.getAttributes().get("loginUser");
					UserInfo loginUser = new UserInfo() ;
					if(obj != null && obj instanceof UserInfo){
						loginUser = (UserInfo)obj;
					}
					String str = null;
					try {
						Object message = method.invoke(clazz,loginUser);
						//str = "99+:99+:0:29";
						WebSocketSessionUtils.sendMessage(session,new TextMessage(message.toString()));
						logger.info("消息发送成功：account:"+loginUser.getName()+" === 请求：URL:"+session.getUri().toString());
					} catch (Exception e) {
						logger.error("消息发送失败：account:"+loginUser.getName()+" === 请求：URL:"+session.getUri().toString(),e);
					}
				}
			}
		}
		logger.info("发送当前时间为:===" + new Date());
		Long endTime = System.currentTimeMillis();
		logger.info("发送消息耗时:===" + (endTime - startTime) + "ms");
	}
	
	/**
	 * <p>
	 * 	获得所有在线的： websocket连接用户列表
	 * </p>
	 * @author wanghuihui
	 * @date 2017-6-2下午3:39:54
	 * @return
	 */
	public static List<UserInfo> getAllWebSocketUsers(){
		Iterator<Entry<String, WebSocketSession>> iterator = clients.entrySet().iterator();
		List<UserInfo> userList = null;
		if(clients.size() == 0){
			return userList;
		}else{
			userList = new ArrayList<UserInfo>(clients.size());
		}
		while (iterator.hasNext()) {
			Map.Entry<String, WebSocketSession> entry = (Map.Entry<String, WebSocketSession>) iterator.next();
			WebSocketSession session = entry.getValue();
			Object obj = session.getAttributes().get("loginUser");
			JSONObject temp = JSONObject.fromObject(obj);
			UserInfo loginUser = (UserInfo) JSONObject.toBean(temp, UserInfo.class);
			userList.add(loginUser);
		}
		return userList;
	}
	
	/**
	 * <p>
	 *  给用户发送消息
	 * </p>
	 * @author wanghuihui
	 * @date 2017-8-3下午7:48:38
	 * @param user：用户信息
	 * @param url ：消息路径
	 * @param content ：消息内容
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public static void sendMessage(UserInfo user,String url, String content) throws InstantiationException, IllegalAccessException {
		if(user == null || user.getName() == null ||StringUtils.isEmpty(url)){
			logger.error("消息发送的用户，或者路径不能为空！");
			return;
		}
		Long startTime = System.currentTimeMillis();
		logger.info("消息发送开始=====:url="+url+" ======:userAccount="+user+"======时间："+DateUtil.datesToString(new Date())+" ");
		List<WebSocketSession> lists = getSession(user,url);
		for(WebSocketSession session : lists){
			WebSocketSessionUtils.sendMessage(session,new TextMessage(content));
		}
		Long endTime = System.currentTimeMillis();
		logger.info("发送消息耗时:===" + (endTime - startTime) + "ms");
	}
	
	/**
	 * <p>
	 *  获得当前用记的SESSION
	 * </p>
	 * @author wanghuihui
	 * @date 2017-8-3下午7:55:16
	 * @param user : 用户 不能为空。
	 * @param url ： 路径，不能为空
	 * @return
	 */
	private static List<WebSocketSession> getSession(UserInfo user, String url) {
		Iterator<Entry<String, WebSocketSession>> iterator = clients.entrySet().iterator();
		List<WebSocketSession> result = new ArrayList<WebSocketSession>(); 
		while (iterator.hasNext()) {
			Map.Entry<String, WebSocketSession> entry = (Map.Entry<String, WebSocketSession>) iterator.next();
			WebSocketSession session = entry.getValue();
				//发送对应URL的消息
				if(url.endsWith(session.getUri().toString()) ){
					Object obj = session.getAttributes().get("loginUser");
					JSONObject temp = JSONObject.fromObject(obj);
					UserInfo loginUser = (UserInfo) JSONObject.toBean(temp, UserInfo.class);
					if(true){//根据条件增加
						result.add(session) ;
					}
				}
		}
		return result ; 
	}
}
