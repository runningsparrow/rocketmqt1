package com.sparrow;

import com.sparrow.rocketmq.ProducerService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Hello world!
 *
 */
@SpringBootApplication
public class RocketmqProducerApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(RocketmqProducerApplication.class, args);
        ProducerService producerService = (ProducerService) run.getBean("producerService");
        producerService.sendMessage();
    }
}