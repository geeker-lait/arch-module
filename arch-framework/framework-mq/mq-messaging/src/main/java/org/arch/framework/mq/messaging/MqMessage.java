package org.arch.framework.mq.messaging;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MqMessage implements Serializable {
    private static final long serialVersionUID = 11564823561324112L;
    private HashMap<String, Object> messageParamValues;
    private Map<String, Object> attachment;
}
