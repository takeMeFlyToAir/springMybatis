package com.zzr.controller;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KafKaConsumer implements MessageListener<Integer, String> {
  
    public void onMessage(ConsumerRecord<Integer, String> records) {
        System.out.println("====================" + records);  
    }
  
}  