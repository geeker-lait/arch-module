package org.arch.payment.sdk;

public interface DirectiveRequest {
    //List<String> getKeys();

    String getRequestId();

    TransactionType getTransactionType();

    DirectiveRequest setTransactionType(TransactionType transactionType);

}
