package org.arch.framework.automate.generater.properties;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date 3/15/2021 6:19 PM
 */
@Data
public class ParamProperties {
    // input/output
    private String type;
    // 参数类型
    private String javaTyp;
    // 参数名
    private String name;
    // 参数描述
    private String descr;
    // 平铺参数
    List<ParamProperties> childs = new ArrayList<>();

    public static void main(String[] args) {

    }

}