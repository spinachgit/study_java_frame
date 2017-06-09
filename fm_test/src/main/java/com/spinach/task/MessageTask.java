package com.spinach.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.spinach.controller.MessageController;
import com.spinach.webservice.CommonWebsocketHandler;
import com.spinach.webservice.WebsocketContants;

/**
 * <p>
 * 消息中心 相关定时器。
 * </p>
 * 
 * @version 1.0
 * @author lidan
 * @date 2016-10-31
 */
@Component(value = "messageTask")
public class MessageTask{
	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(MessageTask.class);
	
	@Autowired
	private CommonWebsocketHandler commonWebsocketHandler;
	
	/**
	 * <p>
	 * 根据不能的用户发送不同的信息 ，面向所有用户: 消息1
	 * </p>
	 * @author wanghuihui
	 * @date 2017-5-22下午6:27:38
	 */
	public void selectMessage1() {
		logger.info("aaaaaaaaaaa");
		commonWebsocketHandler.sendAllMessageByUser(WebsocketContants.MESSAGE_SYS_URL,MessageController.class.getName(),"selectMessage1");
	}
	
	/**
	 * <p>
	 * 根据不能的用户发送不同的信息 ，面向所有用户 : 消息2
	 * </p>
	 * @author wanghuihui
	 * @date 2017-5-22下午5:11:17
	 */
	public void selectMessage2() {
		logger.info("bbbbbbb");
		commonWebsocketHandler.sendAllMessageByUser(WebsocketContants.MESSAGE_AIR_URL,MessageController.class.getName(),"selectMessage2");
	}
	
	
	
}
