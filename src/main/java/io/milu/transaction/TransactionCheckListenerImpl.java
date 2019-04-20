package io.milu.transaction;

import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionCheckListener;
import org.apache.rocketmq.common.message.MessageExt;

public class TransactionCheckListenerImpl implements TransactionCheckListener {
    @Override
    public LocalTransactionState checkLocalTransactionState(MessageExt msg) {

        System.out.println("检查消息事务 " + msg.getMsgId());
        return LocalTransactionState.COMMIT_MESSAGE;
    }
}
