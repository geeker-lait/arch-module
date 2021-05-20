package org.arch.framework.automate.common.api;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date :
 */
@Data
@Accessors(chain = true)
public class Curl {
    /**
     * 方法名
     */
    private String name;
    /**
     * 支持http请求类型
     */
    private String httpMethod;
    /**
     * 是否 restful 风格 API
     */
    private boolean restMethod = false;
    /**
     * 方法泛型值: T -> public <T> 方法返回值 方法名称(方法参数);
     */
    private String genericVal;
    /**
     * 方法描述
     */
    private String descr;
    /**
     * 方法请求入参
     */
    private final List<Param> inputParams = new ArrayList<>();
    /**
     * 方法返回值或出参
     */
    private Param outputParam;
    /**
     * 方法注释
     */
    private final Set<Annot> annotations = new HashSet<>();
}
