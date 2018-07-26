package com.example.learnrabbitmq.topic;

import com.example.learnrabbitmq.demo.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Send {

    private static final  String EXCHANGE_NAME = "exchange_name_topic";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();

        //指定交换机为 topic
        channel.exchangeDeclare(EXCHANGE_NAME, "topic");

        String msgString = "商品---";

        //发送消息 同时指定消息的key
        channel.basicPublish(EXCHANGE_NAME, "good.add", null, msgString.getBytes());
        System.out.println("-- send "+msgString);
        channel.close();
        connection.close();
    }
}
