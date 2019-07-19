package io.milu.filter;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.MessageSelector;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.common.message.MessageExt;
import org.slf4j.LoggerFactory;

import java.util.List;

public class PropertiesConsumer {
    public static void main(String [] argv) throws Exception {
        LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
        JoranConfigurator configurator = new JoranConfigurator();
        configurator.setContext(lc);
        lc.reset();
        configurator.doConfigure(System.getProperty("user.dir") + "/conf/logback.xml");

        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("consumer1");
        consumer.setInstanceName(argv[0]);
        consumer.setNamesrvAddr("127.0.0.1:9876");

        consumer.subscribe("TopicTest", MessageSelector.bySql("user_property = 'test' and TAGS='filter_tag_a'"));

        consumer.registerMessageListener((List<MessageExt> msgs, ConsumeConcurrentlyContext context) -> {
            System.out.println(argv[0] + "收到消息");
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });

        consumer.start();

        System.out.println("Consumer Started.");
    }
}
