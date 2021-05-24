package org.arch.framework.mq.producer.processor;

import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;
import org.arch.framework.mq.messaging.M2Utils;
import org.arch.framework.mq.messaging.MqMessage;

import java.util.HashMap;
import java.util.Map;

public class MqMessageProducerUtils {
    public MqMessageProducerUtils() {
    }

    public static SendResult sendMessage2(DefaultMQProducer defaultMQProducer, String topic, Map<String, Object> messageParamValues) {
        Message msg = getMessage(defaultMQProducer, topic, messageParamValues);

        try {
            return defaultMQProducer.send(msg);
        } catch (Exception var5) {
            throw new RuntimeException(var5.getMessage(), var5);
        }
    }

    public static SendResult sendMessage2WithDelayLevel(DefaultMQProducer defaultMQProducer, String topic, Map<String, Object> messageParamValues, int delayLevel) {
        Message msg = getMessage(defaultMQProducer, topic, messageParamValues);
        msg.setDelayTimeLevel(delayLevel);

        try {
            return defaultMQProducer.send(msg);
        } catch (Exception var6) {
            throw new RuntimeException(var6.getMessage(), var6);
        }
    }

    private static Message getMessage(DefaultMQProducer defaultMQProducer, String topic, Map<String, Object> messageParamValues) {
        if (defaultMQProducer == null) {
            throw new NullPointerException("defaultMQProducer cannot be null");
        } else {
            MqMessage message = new MqMessage(new HashMap(messageParamValues), (Map) null);
            byte[] bytes = M2Utils.serialize(message);
            return new Message(topic, bytes);
        }
    }
}
