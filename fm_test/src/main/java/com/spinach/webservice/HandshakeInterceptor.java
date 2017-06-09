package com.spinach.webservice;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import com.spinach.domain.UserInfo;
import com.spinach.utils.JsonUtil;

public class HandshakeInterceptor extends HttpSessionHandshakeInterceptor {
	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(HandshakeInterceptor.class);
	
	@Override
	public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
		HttpSession session = getSession(request);
		if(null != session){
			//String temp = (String) session.getAttribute("loginUser");
			Object temp = session.getAttribute("loginUser");
			logger.info("============ spinach:beforeHandshake:loginUser="+temp);
			//UserInfo loginUser = JsonUtil.toJavaBean(temp, UserInfo.class);
			UserInfo loginUser = (UserInfo)temp;
	    	if(null != loginUser){
	    		attributes.put(session.getId(),loginUser);
	    	}
		}
		return super.beforeHandshake(request, response, wsHandler, attributes);
	}

	@Override
	public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
		//使用默认配置
		super.afterHandshake(request, response, wsHandler, exception);
	}
	
	private HttpSession getSession(ServerHttpRequest request){
		if(request instanceof ServletServerHttpRequest){
			ServletServerHttpRequest serverRequest = (ServletServerHttpRequest) request;
			HttpSession session = serverRequest.getServletRequest().getSession();
			
			//JSONObject obj = JSONObject.fromObject((String) SecurityUtils.getSubject().getSession().getAttribute("loginUser"));
	    	//UserEntity loginUser = (UserEntity) JSONObject.toBean(obj, UserEntity.class);
			System.out.println(session.getAttribute("loginUser"));
			return session;
		}
		return null;
	}

}
