package com.spinach;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.spinach.provider.mail.entity.Mail;
import com.spinach.provider.mail.mq.MQProducer;


/**
 * <B>系统名称：</B><BR>
 * <B>模块名称：</B><BR>
 * <B>中文类名：</B><BR>
 * <B>概要说明：</B><BR>
 * @author whh
 * @since 2014年7月2日
 */
@ContextConfiguration(locations = {"classpath:spring-context.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class TestProducer {
	
	@Autowired
	private MQProducer mqProducer;
	
	@Test
	public void send(){
		Mail mail = new Mail();
		mail.setTo("spinachyou@126.com");
		mail.setSubject("异步发送邮件");
		mail.setContent("Hi,This is a message!");
														
		this.mqProducer.sendMessage(mail);
		System.out.println("mqProducer 发送成功..");		
		
	}

}
