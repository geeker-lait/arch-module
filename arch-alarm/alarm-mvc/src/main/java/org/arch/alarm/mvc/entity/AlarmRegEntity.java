package org.arch.alarm.mvc.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * 履约预警规则维度 预警规则，如： 规则1：运单状态为委托接受，超过X时间未发货的运单，给出提醒。 规则2：运单状态为提货，未到货的运单，如果当前时间减去接单时间，超过标准时效，则给出提醒，将延误。 规则3：运单状态为发货确认或提货，当前时间减去入场时间，超过X时间未出场的，给出提醒。 规则4：运单状态提货，超过X时间，车辆/骑手的LBS地址没有变化，则给出预警提示，送货异常 实体
 *
 * @author lait.zhang@gmail.com
 * @date 2021-05-03 9:33 PM
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@TableName("alarm_reg")
public class AlarmRegEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 类目id
     */
    private Long catid;

    /**
     * 规则名称
     */
    private String regName;

    /**
     * 规则健，key唯一
     */
    private String regKey;

    /**
     * 规则默认值，参考值
     */
    private String regVal;

    /**
     * 规则参考值来源，可以是一个api接口，一条sql语句 如： http://xxx/getDeliveryTime…… sql://192.168.0.1@db@user:pwd/select * from tb……
     */
    private String regDatasource;

    /**
     * 规则对描述
     */
    private String descr;

    /**
     * 条件或条件表达式，如果没有按照coputer的逻辑计算，如果有computer按照该表达式计算
     */
    private String expression;

    /**
     * 对应ofs_alarm_computer表中单一个计算器
     */
    private Long computerId;

    /**
     * 计算器名称
     */
    private String computerName;

    /**
     * 参数计数器，用于分布式计算，收集参数计数
     */
    private Integer paramCount;
    /**
     * 规则模板通知内容中的占位符
     */
    private String placeholderJson;

    /**
     * 创建时间
     */
    private Date ctime;

    /**
     * 租户id
     */
    private Long tenantId;


}
