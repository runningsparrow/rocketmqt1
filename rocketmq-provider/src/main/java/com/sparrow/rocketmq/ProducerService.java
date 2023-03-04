package com.sparrow.rocketmq;

import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;



@Component("producerService")
public class ProducerService {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    /**
     * 发送简单消息
     */
    public void sendMessage(){
        for(int i=0;i<10;i++){
            rocketMQTemplate.convertAndSend("sparrow-rocketmq","rocketmq-sparrow，你好"+i);
        }
    }
}
