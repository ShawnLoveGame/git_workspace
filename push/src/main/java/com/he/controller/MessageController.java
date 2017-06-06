package com.he.controller;

import com.he.message.producerConfig;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by he on 2017/6/6.
 */
@Controller
public class MessageController {


    @RequestMapping("/sendMsg")
    @ResponseBody
    public void sendMsg(String msg){
        KafkaTemplate<String, String> kafkaTemplate =  producerConfig.kafkaTemplate();
        kafkaTemplate.send("test1","测试消息接受发送");

    }
}
