package org.arch.payment.core.bean;

import com.alibaba.fastjson.JSONObject;
import javafx.scene.text.TextBuilder;

import java.io.Serializable;

/**
 *  支付回调通知返回消息
 */
public abstract class PayOutMessage implements Serializable {
    protected String content;
    protected String msgType;


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    /**
     * 获得文本消息builder
     * @return 文本消息builder
     */
    public static TextBuilder TEXT() {
        return new TextBuilder();
    }
    /**
     * 获得XML消息builder
     * @return XML消息builder
     */
    public static XmlBuilder XML() {
        return new XmlBuilder();
    }
    /**
     * 获得Json消息builder
     * @return Json消息builder
     */
    public static JsonBuilder JSON() {
        return new JsonBuilder(new JSONObject());
    }
    public abstract String toMessage();
}
