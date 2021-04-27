package org.arch.framework.automate.xmind.api;

import lombok.Data;

import java.util.List;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date :
 */
@Data
public class Curl {
    // 方法名
    private String name;
    // 支持http请求类型
    private String httpMethod;
    // 方法描述
    private String descr;
    // 方法请求入参
    private List<Param> inputParams;
    // 方法返回值或出参
    private Param outputParam;
}
