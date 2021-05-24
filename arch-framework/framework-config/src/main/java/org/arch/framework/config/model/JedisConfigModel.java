package org.arch.framework.config.model;

import lombok.Data;
import org.arch.framework.config.annotation.Field;

import java.io.Serializable;

@Data
public class JedisConfigModel implements Serializable {
    @Field(label = "集群最大重定向次数")
    private Integer maxRedirects;
    @Field(label = "maxIdle")
    private Integer maxIdle;
    @Field(label = "maxTotal")
    private Integer maxTotal;
    @Field(label = "maxWaitMillis")
    private Integer maxWaitMillis;
    @Field(label = "minIdle")
    private Integer minIdle;
    @Field(label = "testOnBorrow")
    private Boolean testOnBorrow;
    @Field(label = "服务器地址")
    private String address;
    @Field(label = "集群类型")
    private String clusterType;
    @Field(label = "bean后缀")
    private String beanSuffix;
    @Field(label = "redis密码")
    private String password;
}
