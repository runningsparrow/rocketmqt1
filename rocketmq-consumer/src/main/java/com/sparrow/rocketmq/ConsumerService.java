package com.sparrow.rocketmq;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;


/**
 * 此注解监听 主题 sparrow-rcoketmq
 */
@RocketMQMessageListener(topic = "sparrow-rocketmq",consumerGroup = "${rocketmq.consumer.group}")
@Component
public class ConsumerService implements RocketMQListener<String> {

    @Override
    public void onMessage(String message) {
        System.out.println("收到消息内容："+message);
    }
}

