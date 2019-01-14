package com.example.learnrabbitmq.javademo.workfair;

import com.example.learnrabbitmq.javademo.demo.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Send {
    public static final String Queue_Name = "test_work_queue";
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        //1.获取连接
        Connection connection = ConnectionUtils.getConnection();
        //2.获取channel
        Channel channel = connection.createChannel();
        //队列声明
        /**
         * 每个消费者发送确定消息之前，消息队列不发送下一个消息到消费者，一次只处理一个消息
         * 限制发送同一个消费者不得 超过一条消息队列
         */
        int prefetchConut = 1;
        channel.basicQos(prefetchConut);

        for (int i = 0 ; i < 50 ; i++){
            String msg = "hello " + i;
            System.out.println("send msg:"+msg);
            channel.basicPublish("" , Queue_Name , null , msg.getBytes());
            Thread.sleep(i*20);

        }
        channel.close();
        connection.close();

    }
}
