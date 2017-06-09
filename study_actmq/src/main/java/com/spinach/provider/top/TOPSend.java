package com.spinach.provider.top;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Session;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;

import org.apache.activemq.ActiveMQConnectionFactory;

public class TOPSend {
	    //连接账号
	    private String userName = ActiveMQConnectionFactory.DEFAULT_USER;
	    //连接密码
	    private String password = ActiveMQConnectionFactory.DEFAULT_USER;
	    //连接地址
	    private String brokerURL = "tcp://127.0.0.1:61616";
        
        //目的地，其实就是连接到哪个队列，如果是点对点，那么它的实现是Queue，如果是订阅模式，那它的实现是Topic
        private Topic topic;
        
        
        public static void main(String[] args) {
            TOPSend send = new TOPSend();
            send.start();
        }
        
        public void start(){
            try {
                //根据用户名，密码，url创建一个连接工厂
                //ConnectionFactory factory = new ActiveMQConnectionFactory(userName, password, brokerURL);
            	TopicConnectionFactory factory = new ActiveMQConnectionFactory(userName, password, brokerURL);
            	
                //从工厂中获取一个连接
                //Connection connection = factory.createConnection();
                TopicConnection topicConnection = factory.createTopicConnection();
                //测试过这个步骤不写也是可以的，但是网上的各个文档都写了
                topicConnection.start();
                //创建一个session
                //第一个参数:是否支持事务，如果为true，则会忽略第二个参数，被jms服务器设置为SESSION_TRANSACTED
                //第二个参数为false时，paramB的值可为Session.AUTO_ACKNOWLEDGE，Session.CLIENT_ACKNOWLEDGE，DUPS_OK_ACKNOWLEDGE其中一个。
                //Session.AUTO_ACKNOWLEDGE为自动确认，客户端发送和接收消息不需要做额外的工作。哪怕是接收端发生异常，也会被当作正常发送成功。
                //Session.CLIENT_ACKNOWLEDGE为客户端确认。客户端接收到消息后，必须调用javax.jms.Message的acknowledge方法。jms服务器才会当作发送成功，并删除消息。
                //DUPS_OK_ACKNOWLEDGE允许副本的确认模式。一旦接收方应用程序的方法调用从处理消息处返回，会话对象就会确认消息的接收；而且允许重复确认。
                //Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
                TopicSession topicSession = topicConnection.createTopicSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
                
                //创建一个到达的目的地，其实想一下就知道了，activemq不可能同时只能跑一个队列吧，这里就是连接了一个名为"text-msg"的队列，这个会话将会到这个队列，当然，如果这个队列不存在，将会被创建
                
                
                //=======================================================
                //点对点创建的是Queue，而订阅模式创建的是Topic
                topic = topicSession.createTopic("topic-spinach");
                
                //=======================================================
                
                TopicPublisher publisher = topicSession.createPublisher(topic);
                publisher.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
                
                for(int i=0;i<10;i++){
	                publisher.send(topicSession.createTextMessage("aaa"+i));
	                //sendMessage(topicSession, publisher);
	                System.out.println("消息: aaa"+i);
	                topicSession.commit();
                }
                
                /*
                //生产者，就是产生数据的对象
                MessageProducer producer = session.createProducer(destination);
                producer.setDeliveryMode(DeliveryMode.PERSISTENT);
                producer.send(session.createTextMessage("哈哈"));
                producer.send(session.createObjectMessage("Entity"));
                producer.close();
                */
                
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }

		private void sendMessage(TopicSession topicSession, TopicPublisher publisher) throws JMSException {
			// TODO Auto-generated method stub
			System.out.println("------------发送消息开始-----------");
			MapMessage map = topicSession.createMapMessage();
			map.setString("text", "【this is chauvet's TOPIC message】");
			map.setLong("time", System.currentTimeMillis());
			publisher.send(map);
			System.out.println("------------发送消息结束-----------");
		}
}