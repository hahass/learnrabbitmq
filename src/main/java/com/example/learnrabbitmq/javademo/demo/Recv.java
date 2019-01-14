package com.example.learnrabbitmq.javademo.demo;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Recv {
    public static final String Queue_Name = "test_work_queue";
    public static void main(String[] args) throws IOException, TimeoutException {
        // 1.获取连接
        Connection connection = ConnectionUtils.getConnection();
        // 2. 创建频道
        Channel channel = connection.createChannel();

        // 3. 声明队列
        channel.queueDeclare(Queue_Name,false,false,false,null);

        // 4. 定义一个消费者
        Consumer consumer = new  DefaultConsumer(channel)
        {
            //每次有消息到达就触发该方法
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                super.handleDelivery(consumerTag, envelope, properties, body);
                String msg = new String(body ,"utf-8");
                System.out.println("[1] recv msg:"+msg);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    System.out.println("[1] over");
                }
            }
        };

        //注册消费者，参数2 手动确认， 代表我们收到消息后需要手动告诉服务器，我收到消息了
        channel.basicConsume(Queue_Name, true, consumer);
    }
}
