package com.sparrow.rocketmq;

import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;


/**
 * 此注解监听 主题 sparrow-rcoketmq
 */
//集群模式
//@RocketMQMessageListener(topic = "sparrow-rocketmq",consumerGroup = "${rocketmq.consumer.group}",messageModel = MessageModel.CLUSTERING)
//广播模式
@RocketMQMessageListener(topic = "sparrow-rocketmq",consumerGroup = "${rocketmq.consumer.group}",messageModel = MessageModel.BROADCASTING)
@Component
public class ConsumerService implements RocketMQListener<String> {

    @Override
    public void onMessage(String message) {
        System.out.println("收到消息内容："+message);
    }
}

