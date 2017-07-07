package com.spinach.webservice;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebMvc
@EnableWebSocket
public class WebSocketConfig extends WebMvcConfigurerAdapter implements WebSocketConfigurer {
	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(WebSocketConfig.class);
	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(systemWebSocketHandler(), WebsocketContants.MESSAGE_SYS_URL).addInterceptors(new HandshakeInterceptor());
		registry.addHandler(systemWebSocketHandler(), WebsocketContants.MESSAGE_SYS_SOCKJS_URL).addInterceptors(new HandshakeInterceptor()).withSockJS();
		
		registry.addHandler(systemWebSocketHandler(), WebsocketContants.MESSAGE_AIR_URL).addInterceptors(new HandshakeInterceptor());
		registry.addHandler(systemWebSocketHandler(), WebsocketContants.MESSAGE_AIR_SOCKJS_URL).addInterceptors(new HandshakeInterceptor()).withSockJS();
	}

	@Bean
	public WebSocketHandler systemWebSocketHandler() {
		return new SystemWebSocketHandler();
	}
	
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}
}
