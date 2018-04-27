package com.taotao.mq;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.Test;

import javax.jms.*;

/**
 * Created by len on 2018/4/27.
 *
 * 测试topic模式的消息传递一个生产者产生的消息可以被多个消费者同时消费
 */
public class TestTopicActiveMQ {

    @Test
    public void testTopicProducer() throws Exception {
        // 创建一个连接工厂对象
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.92.129:61616");
        // 使用连接工厂对象来创建一个连接
        Connection connection = connectionFactory.createConnection();
        // 开启连接
        connection.start();
        // 使用连接对象创建一个Session对象
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        // 使用Session对象来创建一个Topic
        Topic topic = session.createTopic("spring-topic");
        // 使用Session对象来创建一个Producer，指定其目的地是Topic
        MessageProducer producer = session.createProducer(topic);
        // 创建一个TextMessage对象
        TextMessage message = session.createTextMessage("使用topic来发送的消息");
        // 使用Producer对象来发送消息
        producer.send(message);
        // 关闭资源
        producer.close();
        session.close();
        connection.close();
    }

    @Test
    public void testTopicConsumer() throws Exception {
        // 创建一个连接工厂对象
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.92.129:61616");
        // 使用工厂对象创建一个连接
        Connection connection = connectionFactory.createConnection();
        // 开启连接
        connection.start();
        // 使用连接对象创建一个Session对象
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        // 创建一个Destination对象，使用topic
        Topic topic = session.createTopic("spring-topic");
        // 使用Session对象创建一个消费者
        MessageConsumer consumer = session.createConsumer(topic);
        System.out.println("topic消费者3。。。。");
        // 使用消费者对象接收消息
        consumer.setMessageListener(new MessageListener() {

            @Override
            public void onMessage(Message message) {
                // 打印消息
                TextMessage textMessage = (TextMessage) message;
                String text = "";
                try {
                    text = textMessage.getText();
                    System.out.println(text);
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });
        // 程序等待
        System.in.read();

        // 关闭资源
        consumer.close();
        session.close();
        connection.close();

    }
}
