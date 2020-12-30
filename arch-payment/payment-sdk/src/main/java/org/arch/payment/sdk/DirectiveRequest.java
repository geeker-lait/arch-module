package org.arch.payment.sdk;

public interface DirectiveRequest {
    String getRequestId();

    TransactionType getTransactionType();

    DirectiveName getDirectiveName();

    DirectiveRequest setRequestId(String requestId);

    DirectiveRequest setTransactionType(TransactionType transactionType);

    DirectiveRequest setDirectiveName(DirectiveName directiveName);


}
