package com.example.learnrabbitmq.routing;

import com.example.learnrabbitmq.demo.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Send {

    public static final String EXECHANGE_NAME = "test_excange_direct";

    public static void main(String[] args) throws IOException, TimeoutException {

        //1.创建连接
        Connection connection = ConnectionUtils.getConnection();

        //2.创建频道
        Channel channel = connection.createChannel();
        //exchange
        /**
         * Declare an exchange.
         * @see com.rabbitmq.client.AMQP.Exchange.Declare
         * @see com.rabbitmq.client.AMQP.Exchange.DeclareOk
         * @param exchange the name of the exchange
         * @param type the exchange type  有direct、fanout、topic三种
         * @param durable true if we are declaring a durable exchange (the exchange will survive a server restart)
         * @param autoDelete true if the server should delete the exchange when it is no longer in use
         * @param arguments other properties (construction arguments) for the exchange
         * @return a declaration-confirm method to indicate the exchange was successfully declared
         * @throws java.io.IOException if an error is encountered
         *
         * Exchange.DeclareOk exchangeDeclare(String exchange, String type, boolean durable, boolean autoDelete,
         */
        //3.创建交换机，指定路由模式 direct
        channel.exchangeDeclare(EXECHANGE_NAME, "direct");
        String msg = "hello direct";
        // 4.发布消息的时候，指定 key 信息
        String routingKey = "error";
        channel.basicPublish(EXECHANGE_NAME , routingKey , null , msg.getBytes());
        channel.close();
        connection.close();

    }
}
