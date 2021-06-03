package org.arch.payment.sdk.message;

public class PayTextOutMessage extends PayOutMessage {

    public PayTextOutMessage() {
    }

    @Override
    public String toMessage() {
        return getContent();
    }
}
