package org.arch.project.alarm.entity;

import lombok.Data;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date :
 */
@Data
public class AlarmDicEntity {
    private Long id;
    //来源库
    private String sourceDatabase;
    //来源表
    private String sourceTable;
    //字段名
    private String filedName;
    //字段码
    private String filedCode;
    //字段类型
    private String filedTyp;
}
