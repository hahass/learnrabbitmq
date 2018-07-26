package com.example.learnrabbitmq.bean;

import com.example.learnrabbitmq.config.MQConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class MQReceiver {

    @RabbitListener(queues = MQConfig.QUEUE)
    public void receive(String msg){
        System.out.println("11"+msg);
    }
}
