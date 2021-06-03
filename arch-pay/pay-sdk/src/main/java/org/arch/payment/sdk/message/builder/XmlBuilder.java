package org.arch.payment.sdk.message.builder;

import org.arch.payment.sdk.message.PayOutMessage;
import org.arch.payment.sdk.message.PayXmlOutMessage;

public class XmlBuilder extends BaseBuilder<XmlBuilder, PayOutMessage> {
    private String content;
    private String code;

    public XmlBuilder content(String content) {
        this.content = content;
        return this;
    }

    public XmlBuilder code(String code) {
        this.code = code;
        return this;
    }


    @Override
    public PayOutMessage build() {
        PayXmlOutMessage message = new PayXmlOutMessage();
        setCommon(message);
        message.setContent(content);
        message.setCode(code);
        return message;
    }
}
