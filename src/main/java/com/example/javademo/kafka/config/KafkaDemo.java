package com.example.javademo.kafka.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.stereotype.Component;

@Component 
public class KafkaDemo {
    @Autowired
    private KafkaAdmin kafkaAdmin;
}
