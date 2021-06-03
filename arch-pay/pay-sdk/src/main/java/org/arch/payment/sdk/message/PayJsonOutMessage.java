package org.arch.payment.sdk.message;

public class PayJsonOutMessage extends PayOutMessage {

    public PayJsonOutMessage() {

    }

    @Override
    public String toMessage() {
        return getContent();
    }


}
