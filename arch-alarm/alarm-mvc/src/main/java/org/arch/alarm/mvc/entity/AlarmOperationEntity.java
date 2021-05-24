package org.arch.alarm.mvc.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * 履约预警作业维度 基于作业维度，围绕着仓店和订单，便于知道和定位那个仓店的， 哪个订单，哪个作业人员，做如何的操作等， 这里支持一个订单可以有多个人去作业 实体
 *
 * @author lait.zhang@gmail.com
 * @date 2021-05-03 9:33 PM
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@TableName("alarm_operation")
public class AlarmOperationEntity implements Serializable {

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
     * 订单号，订单号具有业务意义
     */
    private Long orderNo;

    /**
     * 订单类型
     */
    private Integer orderTyp;

    /**
     * 订单渠道，履约|wms|其他....
     */
    private String orderChannel;

    /**
     * 用户id或账号id,来自用户领域或用户中心
     */
    private Long operatorId;

    /**
     * 作业人名称
     */
    private String operatorName;

    /**
     * 作业名称，来自数据字典，打包，拣货，配送......可以涵盖整个履约链路中的作业
     */
    private String workName;

    /**
     * 作业值，与workName一一对应，来自数据字典
     */
    private String workVal;

    /**
     * 作业时间
     */
    private Date ctime;

    /**
     * 租户id
     */
    private Long tenantId;


}
