package com.sparrow.rocketmq;

import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
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




    /**
     * 发送同步顺序消息
     */
    public void sendOrderlyMessage(){
    // hashKey用来计算决定消息发送到哪个消息队列 一般是订单ID，产品ID等
        rocketMQTemplate.syncSendOrderly("sparrow-rocketmq-orderly", "98456231,创 建", "98456231");
        rocketMQTemplate.syncSendOrderly("sparrow-rocketmq-orderly", "98456231,支 付", "98456231");
        rocketMQTemplate.syncSendOrderly("sparrow-rocketmq-orderly", "98456231,完 成", "98456231");
        rocketMQTemplate.syncSendOrderly("sparrow-rocketmq-orderly", "98456232,创 建", "98456232");
        rocketMQTemplate.syncSendOrderly("sparrow-rocketmq-orderly", "98456232,支 付", "98456232");
        rocketMQTemplate.syncSendOrderly("sparrow-rocketmq-orderly", "98456232,完 成", "98456232");
    }

    /**
     * 发送延迟消息
     */
    public void sendDelayMessage(){
        rocketMQTemplate.syncSend("sparrow-rocketmq",MessageBuilder.withPayload("rocketmq延迟1秒消息").build(),3000,1);
        rocketMQTemplate.syncSend("sparrow-rocketmq",MessageBuilder.withPayload("rocketmq延迟5秒消息").build(),3000,2);
        rocketMQTemplate.syncSend("sparrow-rocketmq",MessageBuilder.withPayload("rocketmq延迟10秒消息").build(),3000,3);
    }


    /**
     * 发送事务消息
     */
    public void sendTransactionMessage(){
    // 构造消息
        Message msg = MessageBuilder.withPayload("rocketmq事务消息-01").build();
        rocketMQTemplate.sendMessageInTransaction("sparrow-transaction-rocketmq",msg,null);
    }

    @RocketMQTransactionListener
    class TransactionListenerImpl implements RocketMQLocalTransactionListener {
        //执行本地事物
        @Override
        public RocketMQLocalTransactionState executeLocalTransaction(Message msg, Object arg) {
        // ... local transaction process, return rollback, commit or unknown
            System.out.println("executeLocalTransaction");
            //返回有三种情况 rollback commit 和 unknow
            //如果返回rollback，则直接丢弃消息；
            //返回commit，
            //MQ-SERVER得到commit后，消费端才可以消费消息；
            //返回unknown，
            //MQ-SERVER接收到unknown后，继续等待，然后再执行checkLocalTransaction确认
            //return RocketMQLocalTransactionState.COMMIT;
            return RocketMQLocalTransactionState.UNKNOWN;
        }
        //检查本地事务
        @Override
        public RocketMQLocalTransactionState checkLocalTransaction(Message msg) {
            // ... check transaction status and return rollback, commit or unknown
            System.out.println("checkLocalTransaction");
            //检查也是返回三种状态，与上面执行本地事物结果相同，相应的处理逻辑也相同
            return RocketMQLocalTransactionState.COMMIT;
        }
    }




}
