package org.arch.framework.config.model;

import lombok.Data;
import org.arch.framework.config.annotation.Field;

import javax.validation.constraints.NotBlank;

@Data
public class RocketmqConsumerConfigModel {
    @Field(label = "服务器地址")
    @NotBlank
    private String address;
    private String beanSuffix;
}
