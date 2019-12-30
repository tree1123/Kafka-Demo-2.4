package com.tree1123.provider;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.errors.RetriableException;

import java.util.Properties;

/**
 * @author jn
 * @date 2019/12/30
 */
public class CallBackProvider {
    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.put("bootstrap.servers", "kafka01:9092,kafka02:9092");
        properties.put("acks", "all");
        properties.put("retries", 0);
        properties.put("batch.size", 16384);
        properties.put("linger.ms", 1);
        properties.put("buffer.memory", 33554432);
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        KafkaProducer<String, String> kafkaProducer = new KafkaProducer<String, String>(properties);
        for (int i = 1; i <= 600; i++) {
            //一直等待返回
            //kafkaProducer.send(new ProducerRecord<String, String>("message", "message"+i)).get()

            //异步处理返回
            kafkaProducer.send(new ProducerRecord<String, String>("message", "message"+i),new Callback(){
                public void onCompletion(RecordMetadata metadata, Exception e) {

                    if(e ==null){
                        //正常处理逻辑
                        System.out.println("The offset of the record we just sent is: " + metadata.offset());

                    }else{
                        e.printStackTrace();
                        if(e instanceof RetriableException) {
                            //处理可重试异常
                        } else {
                            //处理不可重试异常
                        }
                    }
                }
            });
            System.out.println("message"+i);
        }
        kafkaProducer.close();
    }
}
