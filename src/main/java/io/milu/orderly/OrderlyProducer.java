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
        DefaultMQProducer producer = new DefaultMQProducer("producer");
        producer.setNamesrvAddr("127.0.0.1:9876");
        producer.start();

        MessageQueueSelector selector = new OrderlyMessageQueueSelector();

        Message msg1 = new Message(
            "OrderlyTest",// topic
            ("Orderly Message 1").getBytes(RemotingHelper.DEFAULT_CHARSET)// body
        );
        producer.send(msg1, selector, 1);

        Message msg2 = new Message(
            "OrderlyTest",// topic
            ("Orderly Message 2").getBytes(RemotingHelper.DEFAULT_CHARSET)// body
        );
        producer.send(msg2, selector, 1);

        producer.shutdown();
    }

    static class OrderlyMessageQueueSelector implements MessageQueueSelector {
        // mqs 表示这个 topic 下所有队列
        @Override
        public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
            Integer id = (Integer) arg;
            int index = id % mqs.size();
            return mqs.get(index);
        }
    }
}
