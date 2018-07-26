package com.example.learnrabbitmq.confirm;

import com.example.learnrabbitmq.demo.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.TimeoutException;

public class Sendyibu {
    private static final String QUEUE_NAME = "queue_name_confirm_2";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        /**
         * 生产者调用confirmSelect 将 channel设置为Confirm模式
         */
        channel.confirmSelect();

        // 未确认的消息标识
        final SortedSet<Long> confirmSet = Collections.synchronizedSortedSet(new TreeSet<Long>());

        //通道添加监听
        channel.addConfirmListener(new ConfirmListener() {
            //没有问题的handleAck
            @Override
            public void handleAck(long l, boolean b) throws IOException {
                if(b){
                    System.out.println("handleack-multiple");
                    confirmSet.headSet(l+1).clear();
                }else{
                    confirmSet.remove(l);
                }
            }


            @Override
            public void handleNack(long l, boolean b) throws IOException {
                if(b){
                    confirmSet.headSet(l+1).clear();
                }else {
                    confirmSet.remove(l);
                }
            }
        });

        String msg = "ss";
        while (true) {
            long seqno = channel.getNextPublishSeqNo();
            channel.basicPublish("" , QUEUE_NAME , null , msg.getBytes());
            confirmSet.add(seqno);
        }

    }
}
