package com.example.learnrabbitmq.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/test/")
public class demoController {

    @Autowired
    MQReceiver mqReceiver;

    @Autowired
    MQSender mqSender;

    @RequestMapping("test")
    @ResponseBody
    public String test(){

        mqSender.sendObject("anhs");
      //  mqReceiver.receive();

        return "seccess";
    }

}
