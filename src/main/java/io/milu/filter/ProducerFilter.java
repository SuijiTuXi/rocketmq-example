package io.milu.filter;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.slf4j.LoggerFactory;

public class ProducerFilter {
    public static void main(String[] args) throws Exception {

        LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
        JoranConfigurator configurator = new JoranConfigurator();
        configurator.setContext(lc);
        lc.reset();
        configurator.doConfigure(System.getProperty("user.dir") + "/conf/logback.xml");

        DefaultMQProducer producer = new DefaultMQProducer("producer_filter");
        producer.setNamesrvAddr("127.0.0.1:9876");
        producer.start();

        producer.send(buildMsg("filter_tag_a", "filter_a_message"));
        producer.send(buildMsg("filter_tag_b", "filter_b_message"));
        producer.send(buildMsg("filter_tag_c", "filter_c_message"));

        producer.shutdown();
    }

    private static Message buildMsg(String tag, String body) throws Exception {
        Message msg = new Message(
          "TopicTest", // topic
          tag, // tag
          body.getBytes(RemotingHelper.DEFAULT_CHARSET) // body
        );

        return msg;
    }
}
