package com.zzr.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

/**
 * Created by zhaozhirong on 2018/12/5.
 */
public class ProducerClient {

    public static void main(String[] args) {
        try {
            ProducerClient client = new ProducerClient();
            Producer<String, String> kafkaProducer = client.initKafkaProducer();
            client.send2Kafka(kafkaProducer,"my-topic",Arrays.asList("zzr","zzr1","zzr1","zzr1"));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public Producer initKafkaProducer(){
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("partitioner.class", "com.zzr.kafka.MyPartition");
        Producer<String, String> kafkaProducer = new KafkaProducer<>(props);
        return  kafkaProducer;
    }

    public void send2Kafka(Producer producer,String topic, List<String> lines) throws InterruptedException, ExecutionException {
        for (int i = 0; i < 10000; i++) {
            // 三个参数分别为topic, key,value，send()是异步的，添加到缓冲区立即返回，更高效。
            producer.send(new ProducerRecord<String, String>("Topic-test1", Integer.toString(i), Integer.toString(i)));
            try {
                Thread.sleep(0);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
