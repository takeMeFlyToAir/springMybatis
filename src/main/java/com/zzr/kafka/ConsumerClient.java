package com.zzr.kafka;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * Created by zhaozhirong on 2018/12/5.
 */
public class ConsumerClient {
    public static void main(String[] args) {
        ConsumerClient consumerClient = new ConsumerClient();
        Consumer consumer = consumerClient.initKafkaProducer();
        List<String> topics = new ArrayList<>();
        topics.add("Topic-test1");
        consumerClient.receive(consumer,topics);
    }

    public Consumer initKafkaProducer(){
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", "test");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        return consumer;
    }

    public void receive(Consumer consumer, List<String>  topics){
        consumer.subscribe(topics);
        while (true) {

            ConsumerRecords<String, String> records = consumer.poll(100);
            for (ConsumerRecord<String, String> record : records)
                System.out.printf("partition============ %d,offset = %d, key = %s, value = %s%n", record.partition(),record.offset(), record.key(), record.value());
        }
    }
}
