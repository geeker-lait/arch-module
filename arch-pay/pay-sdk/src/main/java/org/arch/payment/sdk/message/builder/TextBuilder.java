package org.arch.payment.sdk.message.builder;

import org.arch.payment.sdk.message.PayOutMessage;
import org.arch.payment.sdk.message.PayTextOutMessage;

public class TextBuilder extends BaseBuilder<TextBuilder, PayOutMessage> {
    private String content;

    public TextBuilder content(String content) {
        this.content = content;
        return this;
    }

    @Override
    public PayOutMessage build() {
        PayTextOutMessage message = new PayTextOutMessage();
        setCommon(message);
        message.setContent(content);
        return message;
    }
}
