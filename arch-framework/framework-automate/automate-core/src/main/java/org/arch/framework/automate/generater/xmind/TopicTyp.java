package org.arch.framework.automate.generater.xmind;

import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date 3/12/2021 2:51 PM
 */
public enum TopicTyp {
    PROJECT("project", "项目"),
    MODULE("module", "模块"),
    PKG("pkg", "包"),
    API("api","接口"),
    POST("post","post方法"),
    GET("get","get方法"),
    DELETE("delete","delete方法"),
    PUT("put","put方法"),
    INPUT("input","请求参数"),
    OUTPUT("output","输出参数"),
    ;

    TopicTyp(String type, String description) {
        this.type = type;
        this.description = description;
    }

    private static final List<TopicTyp> VALUES = EnumUtils.getEnumList(TopicTyp.class);

    public static TopicTyp getTopicType(String value) {
        return VALUES.stream().filter((n) -> {
            return StringUtils.equals(n.getType(), value);
        }).findAny().orElse(null);
    }
    private String description;


    private String type;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
