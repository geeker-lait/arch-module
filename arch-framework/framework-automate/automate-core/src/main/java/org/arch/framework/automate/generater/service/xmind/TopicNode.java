package org.arch.framework.automate.generater.service.xmind;

import lombok.Data;

import java.util.List;

/**
 * @author lait.zhang@gmail.com
 * @description:
 * pkg::基础信息
 * post:add:新增货主
 * input:ShipperRequest:货主请求
 * dd:String:货主编码
 * @weixin PN15855012581
 * @date 3/12/2021 2:34 PM
 */
@Data
public class TopicNode {
    private Long id;
    // topic 类型 @TopicTyp
    private String typ;
    // topic 的值 一个name 或namespace
    private String value;
    // topic 的说明
    private String descr;
    // 子节点
    private List<TopicNode> childNodes;
}
