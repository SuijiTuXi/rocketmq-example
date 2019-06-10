package io.milu.orderly;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.util.List;

public class OrderlyProducer {

    public static void main(String[] args) throws Exception {

        DefaultMQProducer producer = new DefaultMQProducer("producer1");

        producer.setNamesrvAddr("127.0.0.1:9876");

        //调用start()方法启动一个producer实例
        producer.start();

        for (int i = 0; i < 10; i++) {
            try {
                Message msg = new Message(
                    "OrderlyTest",// topic
                    "",// tag
                    ("Orderly Message " + i).getBytes(RemotingHelper.DEFAULT_CHARSET)// body
                );

                SendResult sendResult = producer.send(msg, new OrderlyMessageQueueSelector(), null);

                System.out.println(sendResult);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        producer.shutdown();
    }

    static class OrderlyMessageQueueSelector implements MessageQueueSelector {
        @Override
        public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
            return mqs.get(0);
        }
    }
}
