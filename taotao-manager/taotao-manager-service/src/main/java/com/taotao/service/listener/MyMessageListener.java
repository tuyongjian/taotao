package com.taotao.service.listener;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * Created by len on 2018/4/27.
 */
public class MyMessageListener implements MessageListener {


    // onMessage是一个事件，当消息到达的时候，会调用这个方法

    @Override
    public void onMessage(Message message) {
        // 取消息的内容
        try {
            TextMessage textMessage = (TextMessage) message;
            // 取内容
            String text = textMessage.getText();
            // 其他业务逻辑...当消息取到的时候在这里可以干我们的业务逻辑
            //在这里我们测试接受的参数
            System.out.println("---------"+text);


        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
