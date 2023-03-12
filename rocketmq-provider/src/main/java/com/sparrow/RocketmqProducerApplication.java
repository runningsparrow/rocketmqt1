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
        //发送简单消息
        //producerService.sendMessage();
        //发送同步消息
        //producerService.sendSyncMessage();
        //发送异步消息
        //producerService.sendAsyncMessage();
        //发送单向消息
        //producerService.sendOneWayMessage();
        //发送顺序消息
        //producerService.sendOrderlyMessage();
        //发送延迟消息
        //producerService.sendDelayMessage();
        //发送事物消息
        producerService.sendTransactionMessage();
    }
}