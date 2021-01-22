package org.arch.payment.sdk;

public interface DirectiveRequest {
    String getRequestId();

    TransactionType getTransactionType();

    DirectiveCode getDirectiveCode();

    DirectiveRequest setRequestId(String requestId);

    DirectiveRequest setTransactionType(TransactionType transactionType);

    DirectiveRequest setDirectiveCode(DirectiveCode directiveCode);


    /**
     * 转换参数
     * @param payRequest
     * @param payConfigurable
     * @return
     */
    DirectiveRequest convert(PayRequest payRequest, PayConfigurable payConfigurable);


    /**
     * 签名
     * @param payConfigurable#prikey
     * @return
     */
    DirectiveRequest signature(PayConfigurable payConfigurable);

    /**
     * 加密
     * @param #secretKey
     * @return
     */
    default DirectiveRequest encrypt(String secretKey){
        return this;
    }

    /**
     * 解密
     * @param secretKey
     * @return
     */
    default DirectiveRequest decrypt(String secretKey) {
        return this;
    }
}
