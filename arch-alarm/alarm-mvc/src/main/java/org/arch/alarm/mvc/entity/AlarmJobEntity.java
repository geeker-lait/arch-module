package org.arch.alarm.mvc.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * 履约预警job维度， 定义执行job产生相应维度的数据， 实体
 *
 * @author lait.zhang@gmail.com
 * @date 2021-05-03 9:33 PM
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@TableName("alarm_job")
public class AlarmJobEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键|消息id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 定时表达式
     */
    private String cron;

    /**
     * 创建时间
     */
    private Date ctime;

    /**
     * 租户id
     */
    private Long tenantId;


}
