package io.milu;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import io.milu.transaction.TransactionListenerImpl;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.slf4j.LoggerFactory;

public class TransactionProducer {
    public static void main(String[] args) throws Exception {

        LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
        JoranConfigurator configurator = new JoranConfigurator();
        configurator.setContext(lc);
        lc.reset();
        configurator.doConfigure(System.getProperty("user.dir") + "/conf/logback.xml");

        TransactionMQProducer producer = new TransactionMQProducer("producer1");
        producer.setTransactionListener(new TransactionListenerImpl());

        //设置NameServer地址,此处应改为实际NameServer地址，多个地址之间用；分隔
        //NameServer的地址必须有，但是也可以通过环境变量的方式设置，不一定非得写死在代码里
        producer.setNamesrvAddr("127.0.0.1:9876");
        producer.setSendMsgTimeout(100 * 1000);
        producer.setHeartbeatBrokerInterval(10 * 1000);

        //调用start()方法启动一个producer实例
        producer.start();

        try {
            Message msg = new Message(
            "TransactionTest",// topic
            "TagA",// tag
                ("RocketMQ Transaction").getBytes(RemotingHelper.DEFAULT_CHARSET) // body
            );

            producer.sendMessageInTransaction(msg, null);

            //调用producer的send()方法发送消息
            //这里调用的是同步的方式，所以会有返回结果
            //SendResult sendResult = producer.send(msg);

            //打印返回结果，可以看到消息发送的状态以及一些相关信息
            //System.out.println(sendResult);
        } catch (Exception e) {
            e.printStackTrace();
            Thread.sleep(1000);
        }

        //发送完消息之后，调用shutdown()方法关闭producer
        producer.shutdown();
    }
}
