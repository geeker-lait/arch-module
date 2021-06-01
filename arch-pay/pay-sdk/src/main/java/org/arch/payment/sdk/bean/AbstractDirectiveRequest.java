package org.arch.payment.sdk.bean;

import org.arch.payment.sdk.DirectiveRequest;
import org.arch.payment.sdk.PayConfigurable;
import org.arch.payment.sdk.TransactionType;
import org.arch.payment.sdk.enums.DirectiveCode;

/**
 * @author lait.zhang@gmail.com
 * @description: 指令请求
 * @weixin PN15855012581
 * @date 12/26/2020 4:07 PM
 */
public abstract class AbstractDirectiveRequest implements DirectiveRequest {


    @Override
    public String getRequestId() {
        return null;
    }

    @Override
    public TransactionType getTransactionType() {
        return null;
    }

    @Override
    public DirectiveCode getDirectiveCode() {
        return null;
    }

    @Override
    public DirectiveRequest setRequestId(String requestId) {
        return null;
    }

    @Override
    public DirectiveRequest setTransactionType(TransactionType transactionType) {
        return null;
    }

    @Override
    public DirectiveRequest setDirectiveCode(DirectiveCode directiveCode) {
        return null;
    }

    @Override
    public DirectiveRequest convert(PayRequest payRequest, PayConfigurable payConfigurable) {
        return null;
    }

    @Override
    public DirectiveRequest signature(PayConfigurable payConfigurable) {
        return null;
    }


}
