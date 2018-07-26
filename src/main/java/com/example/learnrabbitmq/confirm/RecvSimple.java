package com.example.learnrabbitmq.confirm;

import com.example.learnrabbitmq.demo.ConnectionUtils;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RecvSimple {
    private static final String QUEUE_NAME = "queue_name_confirm_1";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection cOnnection = ConnectionUtils.getConnection();
        Channel channel = cOnnection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        channel.basicConsume(QUEUE_NAME,true,new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {

                System.out.println("recv[confirm]:"+new String(body,"utf-8"));

            }
        });

    }
}
