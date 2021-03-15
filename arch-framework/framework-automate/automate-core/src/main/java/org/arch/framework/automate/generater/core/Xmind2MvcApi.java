package org.arch.framework.automate.generater.core;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date :
 */
@Data
public class Xmind2MvcApi {
    // 所有类名
    private String clazz;
    // 接口名称
    private String name;
    // 接口参数
    private List<Map<String, String>> paramters;
    // 返回值类型
    private String returnTyp;

}
