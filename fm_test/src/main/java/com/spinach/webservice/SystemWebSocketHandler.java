package com.spinach.webservice;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 * <p>
 * websocket系统处理器
 * </p>
 * @author wanghuihui
 * @date 2017-5-26下午3:28:25
 */
public class SystemWebSocketHandler extends TextWebSocketHandler {
	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(SystemWebSocketHandler.class);
	//private static List<WebSocketSession> users = new ArrayList<WebSocketSession>();
	//private static final Map<String,WebSocketSession> users = new HashMap<String,WebSocketSession>();
	
	/**
	 * 
	 */
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		logger.info("==================@@@@@@@@@@@@@@@@@ afterConnectionEstablished @@@@@@@@@@@@@@==================");
		super.afterConnectionEstablished(session);
		//记录连接的SESSION
		WebSocketSessionUtils.add(session);
	}
	
	/**
	 * 发送客户端的消息
	 */
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		//super.handleTextMessage(session, message);
		logger.info("==================@@@@@@@@@@@@@@@@@ handleTextMessage @@@@@@@@@@@@@@==================");
		WebSocketSessionUtils.sendMessage(session, message);
	}

	/**
	 * 客户端发送的消息处理。
	 */
	@Override
	public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
		logger.info("==================@@@@@@@@@@@@@@@@@ handleMessage @@@@@@@@@@@@@@==================");
		session.sendMessage(message);
	}

	/**
	 * 处理错误信息
	 */
	@Override
	public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
		logger.info("==================@@@@@@@@@@@@@@@@@ handleTransportError @@@@@@@@@@@@@@==================");
		if (session.isOpen()) {
			session.close();
		}
		WebSocketSessionUtils.remove(session);
	}
	
	/**
	 * 关闭接连触发
	 */
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
		logger.info("==================@@@@@@@@@@@@@@@@@ afterConnectionClosed @@@@@@@@@@@@@@==================");
		WebSocketSessionUtils.remove(session);
	}

	@Override
	public boolean supportsPartialMessages() {
		return false;
	}

}
