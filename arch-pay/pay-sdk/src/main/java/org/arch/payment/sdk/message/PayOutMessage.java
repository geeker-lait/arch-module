package org.arch.payment.sdk.message;

import com.alibaba.fastjson.JSONObject;
import org.arch.payment.sdk.message.builder.JsonBuilder;
import org.arch.payment.sdk.message.builder.TextBuilder;
import org.arch.payment.sdk.message.builder.XmlBuilder;

import java.io.Serializable;

public abstract class PayOutMessage implements Serializable {
    protected String content;


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 获得文本消息builder
     *
     * @return 文本消息builder
     */
    public static TextBuilder TEXT() {
        return new TextBuilder();
    }

    /**
     * 获得XML消息builder
     *
     * @return XML消息builder
     */
    public static XmlBuilder XML() {
        return new XmlBuilder();
    }

    /**
     * 获得Json消息builder
     *
     * @return Json消息builder
     */
    public static JsonBuilder JSON() {
        return new JsonBuilder(new JSONObject());
    }

    public abstract String toMessage();
}
