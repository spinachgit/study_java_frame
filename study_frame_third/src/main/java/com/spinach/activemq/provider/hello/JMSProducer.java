package com.spinach.activemq.provider.hello;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * 消息的生产者（发送者）
 * 
 * @author Administrator
 * 
 */
public class JMSProducer {

    
    public static void main(String[] args) {
        try {
            //第一步：建立ConnectionFactory工厂对象，需要填入用户名、密码、以及要连接的地址，均使用默认即可，默认端口为"tcp://localhost:61616"
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
                    ActiveMQConnectionFactory.DEFAULT_USER, 
                    ActiveMQConnectionFactory.DEFAULT_PASSWORD, 
                    "failover:(tcp://localhost:61616)?Randomize=false");
            
            //第二步：通过ConnectionFactory工厂对象我们创建一个Connection连接，并且调用Connection的start方法开启连接，Connection默认是关闭的。
            Connection connection = connectionFactory.createConnection();
            connection.start();
            
            //第三步：通过Connection对象创建Session会话（上下文环境对象），用于接收消息，参数配置1为是否启用是事务，参数配置2为签收模式，一般我们设置自动签收。
            //Session session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
            Session session = connection.createSession(Boolean.TRUE, Session.CLIENT_ACKNOWLEDGE);
            
            //第四步：通过Session创建Destination对象，指的是一个客户端用来指定生产消息目标和消费消息来源的对象，在PTP模式中，Destination被称作Queue即队列；在Pub/Sub模式，Destination被称作Topic即主题。在程序中可以使用多个Queue和Topic。
            Destination destination = session.createQueue("HelloWorld");
            
            //第五步：我们需要通过Session对象创建消息的发送和接收对象（生产者和消费者）MessageProducer/MessageConsumer。
            MessageProducer producer = session.createProducer(null);
            System.out.println(producer.getDeliveryMode());
            //第六步：我们可以使用MessageProducer的setDeliveryMode方法为其设置持久化特性和非持久化特性（DeliveryMode），我们稍后详细介绍。
            //DeliveryMode.PERSISTENT 当activemq关闭的时候，队列数据将会被保存
            //DeliveryMode.NON_PERSISTENT 当activemq关闭的时候，队列里面的数据将会被清空
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);//默认会持久化，持久化的消息在服务器挂掉重启以后，消息会保存下来。
            System.out.println(producer.getDeliveryMode());
            
            //第七步：最后我们使用JMS规范的TextMessage形式创建数据（通过Session对象），并用MessageProducer的send方法发送数据。同理客户端使用receive方法进行接收数据。最后不要忘记关闭Connection连接。
            
            for(int i = 0 ; i < 10 ; i ++){
                TextMessage msg = session.createTextMessage("我是消息内容" + i);
                // 第一个参数目标地址
                // 第二个参数 具体的数据信息
                // 第三个参数 传送数据的模式
                // 第四个参数 优先级
                // 第五个参数 消息的过期时间
                //producer.send(destination, msg, DeliveryMode.NON_PERSISTENT, 0 , 1000L);
                producer.send(destination, msg);
                System.out.println("发送消息：" + msg.getText());
                session.commit(); //启用事务时记得提交事务，不然消费端接收不到消息
               // Thread.sleep(1000);
            }
            
            //通道关闭
            if(connection != null){
                connection.close();
            }            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

}