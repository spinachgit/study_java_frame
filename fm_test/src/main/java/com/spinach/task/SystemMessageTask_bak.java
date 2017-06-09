package com.spinach.task;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.spinach.controller.HomeController;
import com.spinach.domain.UserInfo;
import com.spinach.webservice.WebSocketSessionUtils;

/**
 * <p>
 * 消息中心 相关定时器。
 * </p>
 * 
 * @version 1.0
 * @author lidan
 * @date 2016-10-31
 */
public class SystemMessageTask_bak{
	private Map<String,WebSocketSession> clients = WebSocketSessionUtils.clients;
	@Autowired
	private HomeController homeController;
	
	/**
	 * <p>
	 * 给所有人发布消息，用于广播（不区分用户，发布的信息都是一样的）
	 * </p>
	 * @author wanghuihui
	 * @throws Exception 
	 * @date 2017-5-22下午3:28:59
	 */
	public void sendAllMessage() throws Exception {
		Long startTime = System.currentTimeMillis();
		System.out.println("同步BTD开始===" + new Date());
		
        //template.convertAndSend("/topic/systemMessage/selectAcountQcountWcountScountNumber", temp());
        //template.convertAndSend("/topic/systemMessage/selectAcountQcountWcountScountNumber", "99+:99+:0:29");
        //用于广播
        WebSocketSessionUtils.sendMessageToAll("99+:99+:0:29");
        
		System.out.println("同步BTD结束===" + new Date());
		Long endTime = System.currentTimeMillis();
		
		System.out.println("同步BTD花费时间===" + (endTime - startTime) + "ms");
	}
	
	/**
	 * <p>
	 * 根据不能的用户发送不同的信息 ，面向所有用户
	 * </p>
	 * @author wanghuihui
	 * @date 2017-5-22下午5:11:17
	 */
	public void sendAllMessageByUser() {
		Long startTime = System.currentTimeMillis();
		System.out.println("同步BTD开始===" + new Date());
		
		Iterator<Entry<String, WebSocketSession>> iterator = clients.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, WebSocketSession> entry = (Map.Entry<String, WebSocketSession>) iterator.next();
			WebSocketSession session = entry.getValue();
			
			Object obj = session.getAttributes().get("loginUser");
			UserInfo loginUser = new UserInfo() ;
			if(obj != null && obj instanceof UserInfo){
				loginUser = (UserInfo)obj;
			}
	    	String str = null;
	    	try {
	    		//str = systemMesssageCtrl.selectAcountQcountWcountScountNumber(loginUser);
	    		str = "99+:99+:0:29";
	    		WebSocketSessionUtils.sendMessage(session,new TextMessage(str));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	
		}
		
		System.out.println("同步BTD结束===" + new Date());
		Long endTime = System.currentTimeMillis();
		
		System.out.println("同步BTD花费时间===" + (endTime - startTime) + "ms");
	}
	
	/**
	 * <p>
	 * 根据不能的用户发送不同的信息 ，面向所有用户
	 * </p>
	 * @author wanghuihui
	 * @date 2017-5-22下午5:11:17
	 */
	public void sendAllMessageByUserAndUrl(String url) {
		Long startTime = System.currentTimeMillis();
		System.out.println("sendAllMessageByUserAndUrl开始===" + new Date());
		
		Iterator<Entry<String, WebSocketSession>> iterator = clients.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, WebSocketSession> entry = (Map.Entry<String, WebSocketSession>) iterator.next();
			WebSocketSession session = entry.getValue();
			if(url.endsWith(session.getUri().toString())){
				Object obj = session.getAttributes().get("loginUser");
				if(obj != null && obj instanceof UserInfo){
					UserInfo loginUser = (UserInfo)obj;
				}
				String str = null;
				try {
					//str = homeController.sendMessage(null);
					str = "99+:99+:0:29";
					WebSocketSessionUtils.sendMessage(session,new TextMessage(str));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			
		}
		
		System.out.println("同步BTD结束===" + new Date());
		Long endTime = System.currentTimeMillis();
		
		System.out.println("同步BTD花费时间===" + (endTime - startTime) + "ms");
	}
	
	
}
