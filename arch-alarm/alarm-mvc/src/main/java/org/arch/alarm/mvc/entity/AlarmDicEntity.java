package org.arch.alarm.mvc.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * 履约预警规则字典纬度 预警规则字典 实体
 *
 * @author lait.zhang@gmail.com
 * @date 2021-05-03 9:53 PM
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@TableName("alarm_dic")
public class AlarmDicEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
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

    /**
     * 租户id
     */
    private Long tenantId;


}
