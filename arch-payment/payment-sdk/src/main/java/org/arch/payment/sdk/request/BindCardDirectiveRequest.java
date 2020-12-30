package org.arch.payment.sdk.request;

import org.arch.payment.sdk.DirectiveName;
import org.arch.payment.sdk.DirectiveRequest;
import org.arch.payment.sdk.TransactionType;

/**
 * @author lait.zhang@gmail.com
 * @description: 绑卡指令请求
 * @weixin PN15855012581
 * @date 12/26/2020 4:07 PM
 */
public class BindCardDirectiveRequest implements DirectiveRequest {


    @Override
    public String getRequestId() {
        return null;
    }

    @Override
    public TransactionType getTransactionType() {
        return null;
    }

    @Override
    public DirectiveName getDirectiveName() {
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
    public DirectiveRequest setDirectiveName(DirectiveName directiveName) {
        return null;
    }
}
