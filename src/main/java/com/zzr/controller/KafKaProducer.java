package com.zzr.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/kafka")
public class KafKaProducer {  
      
    @RequestMapping(value = "/send", method = RequestMethod.GET)
    public JSONObject save() {
        System.out.println("+++++++++++++++++++++++++++++++");
        kafkaTemplate.sendDefault("HongHu KAFKA分布式消息服务测试");
        return null;  
    }  
      
    @Autowired
    private KafkaTemplate<Integer, String> kafkaTemplate;
      
    
  
}  