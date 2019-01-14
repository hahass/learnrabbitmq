package com.example.learnrabbitmq.javademo.tx;

import com.example.learnrabbitmq.javademo.demo.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Send {
    private static final String QUEUE_NAME = "queuename_tx";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection con = ConnectionUtils.getConnection();
        Channel channel = con.createChannel();

        channel.queueDeclare(QUEUE_NAME , false , false , false, null);

        String msg = "hello tx message";
        try {
            //开启事务 将 channel 设置成 transaction 模式
            channel.txSelect();
            channel.basicPublish("" , QUEUE_NAME , null , msg.getBytes());
            //提交事务
            channel.txCommit();
        } catch (IOException e) {
            e.printStackTrace();
            //h回滚
            channel.txRollback();
        }
        channel.close();
        con.close();

    }
}
