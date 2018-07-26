package com.example.learnrabbitmq.demo;

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
        /**
         * 队列声明
         * param1：队列名称
         * param2：是否持久化（true表示队列将在服务器重启是生存）
         * param3：是否是独占队列（创建者可以使用的私有队列，断开后自动删除）
         * param4：当所有消费者客户端连接断开时是否自动删除队列
         * param5：第五个参数为队列的其他参数
         */
        channel.queueDeclare(Queue_Name,false,false,false,null);
        for (int i = 0 ; i < 50 ; i++){
            String msg = "hello " + i;
            System.out.println("send msg:"+msg);
            /**
             * 发送消息到队列
             * param1：交换机名称
             * param2：队列映射的路由key
             * param3：消息的其他属性
             * param4：发送消息的主体
             */
            channel.basicPublish("" , Queue_Name , null , msg.getBytes());
            Thread.sleep(i*20);
        }
        channel.close();
        connection.close();

    }
}
