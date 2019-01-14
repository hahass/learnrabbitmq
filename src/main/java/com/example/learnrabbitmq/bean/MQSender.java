package com.example.learnrabbitmq.bean;

import com.example.learnrabbitmq.config.MQConfig;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MQSender {
    @Autowired
    RabbitTemplate rabbitTemplate;

    public void sendObject(Object message) {
        rabbitTemplate.convertAndSend(MQConfig.QUEUE,message);
    }
}
