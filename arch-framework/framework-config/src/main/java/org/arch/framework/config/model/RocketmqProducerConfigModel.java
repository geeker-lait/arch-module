package org.arch.framework.config.model;

import lombok.Data;
import org.arch.framework.config.annotation.Field;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class RocketmqProducerConfigModel {
    @Field(label = "bean后缀")
    private String beanSuffix;
    @Field(label = "服务器地址")
    @NotBlank
    private String address;
    @NotNull
    private Boolean retryAnotherBrokerWhenNotStore;
    @Min(100L)
    private int maxMessageSize;
}
