package io.milu.filter;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.slf4j.LoggerFactory;

public class PropertiesProducerFilter {

    public static void main(String[] args) throws Exception {
        LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
        JoranConfigurator configurator = new JoranConfigurator();
        configurator.setContext(lc);
        lc.reset();
        configurator.doConfigure(System.getProperty("user.dir") + "/conf/logback.xml");

        DefaultMQProducer producer = new DefaultMQProducer("producer_filter");
        producer.setNamesrvAddr("127.0.0.1:9876");
        producer.start();

        Message msg = new Message(
            "TopicTest",// topic
            "filter_tag_b",// tag
            ("Hello RocketMQ").getBytes(RemotingHelper.DEFAULT_CHARSET)// body
        );
        msg.putUserProperty("user_property", "test");

        producer.send(msg);

        producer.shutdown();
    }
}
