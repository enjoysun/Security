package com.myou.spring.kafka.Common;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import java.util.Properties;

/*
 * @Time    : 2019/11/22 4:58 PM
 * @Author  : YouMing
 * @Email   : myoueva@gmail.com
 * @File    : KafkaConsumerConfig.java
 * @Software: IntelliJ IDEA
 */
@Configuration
@PropertySource("classpath:kafka.properties")
@ConfigurationProperties(prefix = "producer")
public class KafkaProducerConfig {
    @Autowired
    private Environment environment;

    private Properties properties;

    @Bean
    public KafkaProducer<Object, Object> KafkaConsumerConfig() {
        properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, environment.getProperty("producer.brokerList"));
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.CLIENT_ID_CONFIG, environment.getProperty("producer.clientId"));
        KafkaProducer<Object, Object> kafkaProducer = new KafkaProducer<>(properties);
        return kafkaProducer;
    }
}
