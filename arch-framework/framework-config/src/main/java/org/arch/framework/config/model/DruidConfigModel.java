package org.arch.framework.config.model;

import lombok.Data;
import org.arch.framework.config.annotation.Field;

import java.io.Serializable;

@Data
public class DruidConfigModel implements Serializable {
    @Field(label = "使用的数据库名称")
    private String dbname;
    @Field(label = "password")
    private String password;
    @Field(label = "username")
    private String username;
    @Field(label = "validationQuery")
    private String validationQuery;
    @Field(label = "filters")
    private String filters;
    @Field(label = "testOnReturn")
    private Boolean testOnReturn;
    @Field(label = "testWhileIdle")
    private Boolean testWhileIdle;
    @Field(label = "testOnBorrow")
    private Boolean testOnBorrow;
    @Field(label = "poolPreparedStatements")
    private Boolean poolPreparedStatements;
    @Field(label = "maxActive")
    private Integer maxActive;
    @Field(label = "initialSize")
    private Integer initialSize;
    @Field(label = "minIdle")
    private Integer minIdle;
    @Field(label = "maxWait")
    private Integer maxWait;
    @Field(label = "maxOpenPreparedStatements")
    private Integer maxOpenPreparedStatements;
    @Field(label = "timeBetweenEvictionRunsMillis")
    private Integer timeBetweenEvictionRunsMillis;
    @Field(label = "minEvictableIdleTimeMillis")
    private Integer minEvictableIdleTimeMillis;
    @Field(label = "jdbcurl")
    private String url;
    @Field(label = "bean后缀")
    private String beanSuffix;
}
