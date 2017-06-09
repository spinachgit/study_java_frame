package com.spinach.controller;

import java.util.Date;
import java.util.Random;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.spinach.domain.Greeting;
import com.spinach.domain.HelloMessage;
import com.spinach.domain.UserInfo;
/**
 *          由客户端触发，并接受服务器发送信息的例子
 */
@Controller
public class MessageController {
	private Random rand = new Random(System.currentTimeMillis());
	
	@RequestMapping(value="/hello",method={RequestMethod.GET})
	public String toHello(){
		System.out.println("messageController ----> tohello()");
		return "hello";
	}
	
    @MessageMapping("/greeting")
    @SendTo("/topic/greetings")
    public Greeting greeting(HelloMessage message) throws Exception {
        System.out.println("MessageController====================================>客户端连接");
        return new Greeting("["+(new Date())+"]  服务器返回: Hello,客户端输入信息< " + message.getName() + ">");
    }
    
    
    public String selectMessage1(UserInfo user) throws Exception {
		return  rand.nextInt(100) + user.getName()+"_message111";
	}
	
	public String selectMessage2(UserInfo user) throws Exception {
		return  rand.nextInt(100) +  user.getName()+"_message222";
	}
}