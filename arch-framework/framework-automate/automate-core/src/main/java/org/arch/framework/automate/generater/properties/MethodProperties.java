package org.arch.framework.automate.generater.properties;

import lombok.Data;

import java.util.List;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date 3/15/2021 6:16 PM
 */
@Data
public class MethodProperties {
    // 方法名
    private String name;
    // http请求方法（post/get/delete/update/....）
    private String httpMethod;
    // 方法描述
    private String descr;
    // 方法输入参数
    private List<ParamProperties> input;
    // 方法输出参数
    private ParamProperties output;
}
