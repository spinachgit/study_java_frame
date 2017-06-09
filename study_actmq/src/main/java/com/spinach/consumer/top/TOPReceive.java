package com.spinach.consumer.top;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTopic;

public class TOPReceive {
	// 连接账号
	private String userName = ActiveMQConnectionFactory.DEFAULT_USER;
	// 连接密码
	private String password = ActiveMQConnectionFactory.DEFAULT_USER;
	// 连接地址
	private String brokerURL = "tcp://127.0.0.1:61616";
	/**
	 * connection的工厂 点对点：ConnectionFactory
	 */
	private TopicConnectionFactory factory;
	/**
	 * 连接对象 TopicConnection Connection
	 */
	private TopicConnection connection;
	/**
	 * 一个操作会话 Session TopicSession
	 */
	private TopicSession session;
	/**
	 * 目的地，其实就是连接到哪个队列，如果是点对点，那么它的实现是Queue，如果是订阅模式，那它的实现是Topic Destination Topic
	 */
	private Topic topic;
	// 生产者，就是产生数据的对象
	private MessageConsumer consumer;

	public static void main(String[] args) {
		TOPReceive send = new TOPReceive();
		send.start();
	}

	public void start() {
		try {
			// 根据用户名，密码，url创建一个连接工厂
			factory = new ActiveMQConnectionFactory(userName, password, brokerURL);
			// 从工厂中获取一个连接
			// connection = factory.createConnection();
			connection = factory.createTopicConnection();

			// 测试过这个步骤不写也是可以的，但是网上的各个文档都写了
			connection.start();
			// 创建一个session
			// 第一个参数:是否支持事务，如果为true，则会忽略第二个参数，被jms服务器设置为SESSION_TRANSACTED
			// 第二个参数为false时，paramB的值可为Session.AUTO_ACKNOWLEDGE，Session.CLIENT_ACKNOWLEDGE，DUPS_OK_ACKNOWLEDGE其中一个。
			// Session.AUTO_ACKNOWLEDGE为自动确认，客户端发送和接收消息不需要做额外的工作。哪怕是接收端发生异常，也会被当作正常发送成功。
			// Session.CLIENT_ACKNOWLEDGE为客户端确认。客户端接收到消息后，必须调用javax.jms.Message的acknowledge方法。jms服务器才会当作发送成功，并删除消息。
			// DUPS_OK_ACKNOWLEDGE允许副本的确认模式。一旦接收方应用程序的方法调用从处理消息处返回，会话对象就会确认消息的接收；而且允许重复确认。
			// session = connection.createSession(false,
			// Session.AUTO_ACKNOWLEDGE);
			session = connection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);

			// =======================================================
			// 点对点与订阅模式唯一不同的地方，就是这一行代码，点对点创建的是Queue，而订阅模式创建的是Topic
			topic = session.createTopic("topic-spinach");

			// =======================================================

			// 从session中，获取一个消息生产者
			// consumer = session.createConsumer(topic);
			TopicSubscriber consumer = session.createSubscriber(topic);
			//receiveMessage(consumer);
			consumer.setMessageListener(new MessageListener() {
				public void onMessage(Message msg) {
					try {
						System.out.println("Consumer get " + ((TextMessage) msg).getText());
						
						msg.acknowledge(); //如果session.CLIENT_ACKNOWLEDGE
	                	System.out.println(session.getAcknowledgeMode());
	                	session.commit(); //如果：connection.createSession(Boolean.TRUE,"xxx")
						
					} catch (JMSException e) {
						e.printStackTrace();
					}
				}
			});

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (consumer != null) {
				try {
					consumer.close();
				} catch (JMSException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
			if (session != null) {
				try {
					session.close();
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		}

	}
	
	/**
	 * <p>
	 *	接收消息侦听
	 * </p>
	 * @author spinach
	 * 2017年6月2日下午4:11:15
	 * @param consumer
	 * @throws JMSException
	 */
	private void receiveMessage(TopicSubscriber consumer) throws JMSException {
		consumer.setMessageListener(new MessageListener() {
			public void onMessage(Message msg) {
				if (msg != null) {
					MapMessage map = (MapMessage) msg;
					try {
						System.out.println(map.getLong("time") + "接收#" + map.getString("text"));
					} catch (JMSException e) {
						e.printStackTrace();
					}
				}
			}
		});
	}
}