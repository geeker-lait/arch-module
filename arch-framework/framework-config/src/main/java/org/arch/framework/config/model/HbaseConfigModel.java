package org.arch.framework.config.model;

import lombok.Data;
import org.arch.framework.config.annotation.Field;

import java.io.Serializable;

@Data
public class HbaseConfigModel implements Serializable {
    @Field(label = "maxThread")
    private Integer maxThread;
    @Field(label = "hbaseZookeeper地址")
    private String address;
    @Field(label = "zookeeper port")
    private String clientPort;
    @Field(label = "bean后缀")
    private String beanSuffix;
}
