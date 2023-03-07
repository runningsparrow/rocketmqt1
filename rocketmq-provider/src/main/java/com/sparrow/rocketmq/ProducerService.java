package com.sparrow.rocketmq;

import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
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


    /**
     * 发送同步消息
     */
    public void sendSyncMessage(){
        for(int i=0;i<10;i++){
            SendResult sendResult = rocketMQTemplate.syncSend("sparrow-rocketmq","rocketmq-sparrow同步消息！"+i);
            System.out.println(sendResult);
        }
    }


    /**
     * 发送异步消息
     */
    public void sendAsyncMessage(){
        for(int i=0;i<10;i++){
            rocketMQTemplate.asyncSend("sparrow-rocketmq", "rocketmg-sparrow异步消息！"+i, new SendCallback() {
                        @Override
                        public void onSuccess(SendResult sendResult) {
                            System.out.println("发送成功！");
                        }
                        @Override
                        public void onException(Throwable throwable) {
                            System.out.println("发送失败！");
                        }
                    });
        }
    }



    /**
     * 发送单向消息
     */
    public void sendOneWayMessage(){
        for(int i=0;i<10;i++){
            rocketMQTemplate.sendOneWay("sparrow-rocketmq", "rocketmg-sparrow单向消息！"+i);
        }
    }



}
