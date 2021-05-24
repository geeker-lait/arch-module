package org.arch.framework.config.model;

import lombok.Data;
import org.arch.framework.config.annotation.Field;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class EsHighRestConfigModel implements Serializable {
    @NotBlank
    @Field(label = "password")
    private String password;
    @NotBlank
    @Field(label = "username")
    private String username;
    @Field(label = "bean后缀")
    private String beanSuffix;
    @NotBlank
    @Field(label = "服务器地址")
    private String address;
    @Field(label = "集群名称")
    private String name;
    private Integer connectTimeoutMs = 10000;
    private Integer socketTimeoutMs = 60000;
    private Integer connectionRequestTimeoutMs = 60000;
}
