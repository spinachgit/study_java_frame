package com.spinach.webservice;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import com.spinach.utils.JsonUtil;

/**
 * <p>
 * 获得websocket公用类
 * </p>
 * @author wanghuihui
 * @date 2017-5-22下午1:38:49
 */
public class WebSocketSessionUtils {
	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(WebSocketSessionUtils.class);
	public static Map<String,WebSocketSession> clients = new ConcurrentHashMap<String,WebSocketSession>();
	
	/**
	 * <p>
	 * WebSocketSession增加:每当一个页面刷新的时候，会有一个新的握手，新建一个websocket会话
	 * </p>
	 * @author wanghuihui
	 * @date 2017-5-22下午1:40:31
	 * @param session
	 */
	public static void add(WebSocketSession session) {
		logger.error("==========@@@@@@@@@ addSession:sessionId="+session.getId()+ " @@@@@ RemoteAdd:"+session.getRemoteAddress()	+ " @@@@@ LocalAdd:"+session.getLocalAddress());
		if(clients.containsKey(getSessionId(session))){
			clients.remove(getSessionId(session));
		}
		clients.put(getSessionId(session), session);
	}
	
	/**
	 * <p>
	 * WebSocketSession删除:有websocket连接的页面，关闭或者刷新 会调用一次，保持会话的最新
	 * </p>
	 * @author wanghuihui
	 * @date 2017-5-22下午1:40:31
	 * @param session
	 */
	public static void remove(WebSocketSession session){
		logger.error("==========@@@@@@@@@ removeSession:sessionId="+session.getId()+ " @@@@@ RemoteAdd:"+session.getRemoteAddress()	+ " @@@@@ LocalAdd:"+session.getLocalAddress());
		clients.remove(getSessionId(session));
	}
	
	/**
	 * <p>
	 * 当前websocet接连数
	 * </p>
	 * @author wanghuihui
	 * @date 2017-5-22下午1:45:50
	 * @return
	 */
	public static int size(){
		return clients.size();
	}
	
	/**
	 * <p>
	 * websocket信息发送方法
	 * </p>
	 * @author wanghuihui
	 * @date 2017-5-22下午1:49:14
	 * @param message
	 * @param session
	 */
	public static void sendMessage(WebSocketSession session,WebSocketMessage<?> message) {
		if(session != null && session.isOpen()){
			try {
				logger.info("==================@@@@@@@@@@@@@@@@@ sendMessage sessionId="+session.getId());
				session.sendMessage(message);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * <p>
	 * 给所有激活窗口发送信息
	 * </p>
	 * @author wanghuihui
	 * @date 2017-5-22下午1:52:36
	 * @param message
	 */
	public static void sendMessageToAll(WebSocketMessage<?> message) {
		Iterator<Entry<String, WebSocketSession>> iterator = clients.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, WebSocketSession> entry = (Map.Entry<String, WebSocketSession>) iterator.next();
			WebSocketSession session = entry.getValue();
			sendMessage(session,message);
		}
	}
	/**
	 * <p>
	 * 给所有激活窗口发送信息:发送对象
	 * </p>
	 * @author wanghuihui
	 * @date 2017-5-22下午3:24:56
	 * @param obj
	 * @throws Exception 
	 */
	public static void sendMessageToAll(Object obj) throws Exception {
		logger.info("==================@@@@@@@@@@@@@@@@@ sendMessageToAll start");
		String temp = JsonUtil.getJSONString(obj);
		sendMessageToAll(new TextMessage(temp));
	}
	
	private static String getSessionId(WebSocketSession session) {
		return "ws_id_"+session.getId();
	}
	
	/**
	 * <p>
	 * 根据URL获得对应的SESSIONS信息: 注不建议使用些方法,如果定时器很频繁的话,会产生很多对象.给内存造成负担
	 * 建议:把当前方法拷贝到对应代码,在IF中执行自己的代码.
	 * </p>
	 * @author wanghuihui
	 * @date 2017-5-23下午3:08:28
	 * @return
	 */
	public Map<String,WebSocketSession> getSessionByUrl(String url){
		Iterator<Entry<String, WebSocketSession>> iterator = clients.entrySet().iterator();
		Map<String,WebSocketSession> map = new HashMap<String, WebSocketSession>();
		while (iterator.hasNext()) {
			Map.Entry<String, WebSocketSession> entry = (Map.Entry<String, WebSocketSession>) iterator.next();
			WebSocketSession session = entry.getValue();
			if(url.endsWith(session.getUri().toString())){
				map.put(getSessionId(session), session);
			}
		}
		return map;
	}
}
