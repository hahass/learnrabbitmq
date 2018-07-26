package com.example.learnrabbitmq.confirm;

import com.example.learnrabbitmq.demo.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 普通模式
 */
public class SendSimple {

    private static final String QUEUE_NAME = "queue_name_confirm_1";

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        /**
         * 生产者调用confirmSelect 将 channel设置为Confirm模式
         */
        channel.confirmSelect();

        String msg = "hello confirm message";
        channel.basicPublish("",QUEUE_NAME , null , msg.getBytes());

        if (channel.waitForConfirms()) {
            System.out.println("send ok");
        }else {
            System.out.println("send fail");
        }
        channel.close();
        connection.close();
    }

}
