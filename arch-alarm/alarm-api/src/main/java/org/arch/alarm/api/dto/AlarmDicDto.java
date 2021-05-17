package org.arch.alarm.api.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date 5/6/2021 11:14 AM
 */
@Data
public class AlarmDicDto implements Serializable {

    /**
     * 主键
     */
    private Long id;

    /**
     * 来源库
     */
    private String sourceDatabase;

    /**
     * 来源表
     */
    private String sourceTable;

    /**
     * 字段名
     */
    private String filedName;

    /**
     * 字段码
     */
    private String filedCode;

    /**
     * 字段类型
     */
    private String filedTyp;

    /**
     * 创建时间
     */
    private Date ctime;
}
