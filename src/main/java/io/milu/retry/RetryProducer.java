package io.milu.retry;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

public class RetryProducer {
    public static void main(String[] args) throws Exception {

        DefaultMQProducer producer = new DefaultMQProducer("producerRetry");

        producer.setNamesrvAddr("127.0.0.1:9876");

        //调用start()方法启动一个producer实例
        producer.start();

        for (int i = 0; i < 1; i++) {
            try {
                Message msg = new Message(
                        "RetryTest",// topic
                        "",// tag
                        ("Orderly Message " + i).getBytes(RemotingHelper.DEFAULT_CHARSET)// body
                );

                SendResult sendResult = producer.send(msg);

                System.out.println(sendResult);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        producer.shutdown();
    }
}
