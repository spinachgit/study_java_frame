
package com.spinach.activemq.consumer.mail.mq;

import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.SessionAwareMessageListener;
import org.springframework.stereotype.Component;

import com.spinach.activemq.consumer.mail.entity.Mail;
import com.spinach.activemq.consumer.mail.service.MailService;
import com.alibaba.fastjson.JSONObject;

/**
 * <B>系统名称：</B><BR>
 * <B>模块名称：</B><BR>
 * <B>中文类名：</B><BR>
 * <B>概要说明：</B><BR>
 * @author whh
 * @since 2014年7月2日
 */
@Component
public class MailQueueMessageListener implements SessionAwareMessageListener<Message> {

	@Autowired
	private JmsTemplate jmsTemplate;
	@Autowired
	private Destination mailQueue;
	@Autowired
	private MailService mailService;

	public synchronized void onMessage(Message message, Session session) {
		try {
			TextMessage msg = (TextMessage) message;
			final String ms = msg.getText();
			System.out.println("consumer:收到消息：" + ms);
			//转换成相应的对象
			Mail mail = JSONObject.parseObject(ms, Mail.class);
			if (mail == null) {
				return;
			}
			try {
				//执行发送业务
				mailService.mailSend(mail);
				
			} catch (Exception e) {
				// 发送异常，重新放回队列
//				jmsTemplate.send(mailQueue, new MessageCreator() {
//					@Override
//					public Message createMessage(Session session) throws JMSException {
//						return session.createTextMessage(ms);
//					}
//				});
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
