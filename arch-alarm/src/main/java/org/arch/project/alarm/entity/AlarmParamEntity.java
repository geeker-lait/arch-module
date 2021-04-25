package org.arch.project.alarm.entity;

import lombok.Builder;
import lombok.Data;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date :
 */
@Data
@Builder
public class AlarmParamEntity {
    // 主键
    private Long id;
    //字典id
    private Long dicId;
    //规则j计算beanId
    private Long computerId;
    //字段名
    private String filedName;
    //字段码
    private String filedCode;
    //字段类型
    private String filedTyp;
}
