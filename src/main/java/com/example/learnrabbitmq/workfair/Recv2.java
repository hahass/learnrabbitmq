package com.example.learnrabbitmq.workfair;

import com.example.learnrabbitmq.demo.ConnectionUtils;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Recv2 {
    public static final String Queue_Name = "test_work_queue";
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtils.getConnection();
        final Channel channel = connection.createChannel();

        //声明队列
         channel.queueDeclare(Queue_Name,false,false,false,null);
        //保证一次只分发一个
        channel.basicQos(1);
        //定义一个消费者
        Consumer consumer = new  DefaultConsumer(channel)
        {
            //消息到达出发方法
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                super.handleDelivery(consumerTag, envelope, properties, body);
                String msg = new String(body ,"utf-8");
                System.out.println("[2] recv msg:"+msg);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    System.out.println("[2] over");
                    channel.basicAck(envelope.getDeliveryTag(),false);
                }
            }
        };

        channel.basicConsume(Queue_Name, false, consumer);
        

    }
}
