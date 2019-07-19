package io.milu.hook;

import org.apache.rocketmq.client.hook.SendMessageContext;
import org.apache.rocketmq.client.hook.SendMessageHook;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

public class HookProducer {

    public static void main(String[] args) throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer("trace", true);

        producer.setNamesrvAddr("127.0.0.1:9876");

        //调用start()方法启动一个producer实例
        producer.start();

        Message msg = new Message(
            "TraceTest",// topic
            ("HookTest").getBytes(RemotingHelper.DEFAULT_CHARSET) // body
        );
        SendResult sendResult = producer.send(msg);

        System.out.println(sendResult);

        producer.shutdown();
    }
}
