package com.example.learnrabbitmq.bean;

import com.example.learnrabbitmq.config.MQConfig;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MQSender {
    @Autowired
    AmqpTemplate amqpTemplate;

    public void sendObject(Object message) {
        amqpTemplate.convertAndSend(MQConfig.QUEUE,message);
    }
}
