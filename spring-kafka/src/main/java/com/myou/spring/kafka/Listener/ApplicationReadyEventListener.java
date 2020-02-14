package com.myou.spring.kafka.Listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/*
 * @Time    : 2019/11/21 4:19 PM
 * @Author  : YouMing
 * @Email   : myoueva@gmail.com
 * @File    : ApplicationReadyEventListener.java
 * @Software: IntelliJ IDEA
 */
@Slf4j
@Component
public class ApplicationReadyEventListener implements ApplicationListener<ApplicationReadyEvent> {
    /**
     * springboot加载完成调用事件
     */


    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
//        KafkaConsumerConfig kafkaConsumerConfig = new KafkaConsumerConfig("group-1", null);
//        KafkaConsumer consumer = kafkaConsumerConfig.kafkaConsumer(Arrays.asList("topic-test"));
//        try {
//            for (; ; ) {
//                ConsumerRecords<String, String> consumerRecords = consumer.poll(Duration.ofMillis(1000));
//                for (ConsumerRecord<String, String> record : consumerRecords) {
//                    log.info(String.format("offset:%d", record.offset()));
//                    log.info(String.format("value:%s", record.value()));
//                    log.info(String.format("topic:%s", record.topic()));
//                    log.info(String.format("partition:%d", record.partition()));
//                }
//            }
//        } finally {
//            consumer.close();
//        }
    }
}
