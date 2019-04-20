package io.milu.transaction;

import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;

public class TransactionListenerImpl implements TransactionListener  {
    @Override
    public LocalTransactionState executeLocalTransaction(Message msg, Object arg) {

        System.out.println("TransactionListenerImpl#executeLocalTransaction");

        return LocalTransactionState.COMMIT_MESSAGE;
    }

    @Override
    public LocalTransactionState checkLocalTransaction(MessageExt msg) {

        System.out.println("TransactionListenerImpl#checkLocalTransaction");

        return LocalTransactionState.COMMIT_MESSAGE;
    }
}
