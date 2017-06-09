package com.spinach.webservice;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;

@Configuration
//@EnableWebMvc
//@EnableWebSocket
@EnableWebSocketMessageBroker
public class WebSocketMessageConfig implements WebSocketMessageBrokerConfigurer {

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/ws").withSockJS();
	}
	
	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		//设置服务器广播消息的基础路径 
		registry.enableSimpleBroker("/topic");
		//在topic和user这两个域上可以向客户端发消息；
		//registry.enableSimpleBroker("/topic","/user");
		
		//给指定用户发送（一对一）的主题前缀是“/user/”;  
		registry.setUserDestinationPrefix("/user/");  
        
		//设置客户端订阅消息的基础路径  客户端向服务端发送时的主题上面需要加"/app"作为前缀；
		registry.setApplicationDestinationPrefixes("/app");
		//可以用“.” 来分割路径，看看类级别的 @messageMapping和方法级别@messageMapping
		//registry.setPathMatcher(new AntPathMatcher("."));
	}
	
	@Override
	public void configureWebSocketTransport(WebSocketTransportRegistration registry) {
		registry.setMessageSizeLimit(3*1024*1024);
		registry.setSendBufferSizeLimit(6*1024*1024);
		registry.setSendTimeLimit(10000);//设置消息发送时间限制鸿业
		//registry.addDecoratorFactory(new MyWebSocketHandlerDecoratorFactory());
	}
	
	/**
	 * 配置客户连接线程的控制
	 */
	@Override
	public void configureClientInboundChannel(ChannelRegistration registration) {
		registration.taskExecutor().corePoolSize(5).maxPoolSize(8).keepAliveSeconds(60);
	}

	@Override
	public void configureClientOutboundChannel(ChannelRegistration registration) {
		//registration.taskExecutor().corePoolSize(5).maxPoolSize(8).keepAliveSeconds(60);
	}

	@Override
	public boolean configureMessageConverters(List<MessageConverter> messageConverters) {
		return true;
	}

	

	/*@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> arg0) {
		// TODO Auto-generated method stub
		
	}*/
}
