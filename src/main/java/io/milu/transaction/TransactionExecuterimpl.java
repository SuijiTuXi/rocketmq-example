package io.milu.transaction;

import org.apache.rocketmq.client.producer.LocalTransactionExecuter;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.common.message.Message;

public class TransactionExecuterimpl implements LocalTransactionExecuter {
    @Override
    public LocalTransactionState executeLocalTransactionBranch(Message msg, Object arg) {

        System.out.println(new String(msg.getBody()));

        return LocalTransactionState.COMMIT_MESSAGE;
    }
}
