package com.example.learnrabbitmq.javademo.routing;

import com.example.learnrabbitmq.javademo.demo.ConnectionUtils;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Recv {

    public static final String EXECHANGE_NAME = "test_excange_direct";
    public static final String QUEUE_NAME = "test_queue_direct_1";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();

        //1.声明队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        //2.绑定消息队列到交换机，同时指定接受消息的key，可以同时绑定多个key
        channel.queueBind(QUEUE_NAME, EXECHANGE_NAME, "error");
        //同一时刻服务器只会发一条消息给消费者
        channel.basicQos(1);
        Consumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                super.handleDelivery(consumerTag, envelope, properties, body);
                String msg = new String(body, "utf-8");
                System.out.println("[1] recv msg:"+msg);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    System.out.println("[1] over");
                    channel.basicAck(envelope.getDeliveryTag() , false);
                }
               }
        };
        //注册消费者，autoACk 手动确认， 代表我们收到消息后 需要手动告诉服务器，我收到消息了
        boolean autoAck = false;
        channel.basicConsume(QUEUE_NAME, autoAck, consumer);
    }
}
