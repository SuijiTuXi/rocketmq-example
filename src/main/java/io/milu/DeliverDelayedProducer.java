package io.milu;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

public class DeliverDelayedProducer {
    public static void main(String[] args) throws Exception {

        DefaultMQProducer producer = new DefaultMQProducer("producer1");
        producer.setNamesrvAddr("127.0.0.1:9876");
        producer.start();

        String str = "This is a deliver delayed message";
        Message msg = new Message("TopicTest", "TagA", str.getBytes(RemotingHelper.DEFAULT_CHARSET));
        msg.setDelayTimeLevel(2);

        SendResult sendResult = producer.send(msg);

        System.out.println(sendResult);

        //发送完消息之后，调用shutdown()方法关闭producer
        producer.shutdown();

    }
}