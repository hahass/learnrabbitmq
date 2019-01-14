package com.example.learnrabbitmq;

import com.example.learnrabbitmq.bean.MQReceiver;
import com.example.learnrabbitmq.springdemo.Send;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LearnrabbitmqApplicationTests {

    @Autowired
    private Send helloSender;

    @Autowired
    private MQReceiver mqReceiver;

    @Test
    public void hello() {
        helloSender.send();
    }

}
