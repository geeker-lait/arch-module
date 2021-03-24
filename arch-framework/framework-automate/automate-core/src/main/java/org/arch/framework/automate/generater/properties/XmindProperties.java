package org.arch.framework.automate.generater.properties;

import lombok.Data;
import org.arch.framework.automate.generater.core.SchemaMetadata;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date 3/15/2021 6:08 PM
 */
@Data
public class XmindProperties extends MetadataProperties implements SchemaMetadata {
    /**
     * topic typ[TopicTyp.values()]
     * <p>
     * PROJECT("project", "项目"),
     * MODULE("module", "模块"),
     * PKG("pkg", "包"),
     * ENTITY("entity","实体"),
     * API("api","接口"),
     * POST("post","post方法"),
     * GET("get","get方法"),
     * DELETE("delete","delete方法"),
     * PUT("put","put方法"),
     * INPUT("input","请求参数"),
     * OUTPUT("output","输出参数"),
     */
    private String topicTyp;
    // 包
    private String pkg;
    //
    /**
     * topic val()
     * module:${account-entity}:账号实体模块
     * pkg:${pkg}:包
     * entity:${entity}:实体名
     * 取中间值
     */
    private String topicVal;
    /**
     * 说描述明
     */
    private String descr;
    /**
     * 匹配模式（api/mvc/...）
     */
    private String pattern;
    /**
     * 孩子节点
     */
    private List<XmindProperties> childs;

    // 元数据名称
    @Override
    public String getSchemaName() {
        return topicVal;
    }
}
