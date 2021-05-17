package org.arch.alarm.mvc.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * 履约预警订单维度 基于订单维度 实体
 *
 * @author lait.zhang@gmail.com
 * @date 2021-05-03 9:33 PM
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@TableName("alarm_order")
public class AlarmOrderEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 仓店号
     */
    private String storeNo;

    /**
     * 订单号
     */
    private Long orderNo;

    /**
     * 订单类型
     */
    private Integer orderType;

    /**
     * 订单渠道，履约|wms|其他....
     */
    private String orderChannel;

    /**
     * 预警健
     */
    private String regKey;

    /**
     * 预警值
     */
    private String regVal;

    /**
     * 预警名称
     */
    private String regName;

    /**
     * 当前值，实际业务得到的值
     */
    private String curVal;

    /**
     * 规则id
     */
    private Long regId;

    /**
     * 创建时间
     */
    private Date ctime;

    /**
     * 租户id
     */
    private Long tenantId;


}
