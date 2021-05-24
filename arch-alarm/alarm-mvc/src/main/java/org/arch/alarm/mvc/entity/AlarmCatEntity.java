package org.arch.alarm.mvc.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import org.arch.framework.crud.CrudEntity;

import java.io.Serializable;

/**
 * 履约预警分类 预警树结构名称空间，对预警的分类，预警的级别，预警的名称等的定义 实体
 *
 * @author lait.zhang@gmail.com
 * @date 2021-05-03 9:33 PM
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("alarm_cat")
public class AlarmCatEntity extends CrudEntity<AlarmCatEntity> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 是否是类目 0:不是，1:是
     */
    private Integer isCateg;

    /**
     * 父id，用于确定警告的层级关系，用于数据分析
     */
    private Long pid;

    /**
     * 预警分类名： 0：通知类（不可抗力延迟，快递操作延迟，节假日延迟，收货人要求延迟） 1：处理类（签收前破损，禁运品，联系信息有误，联系信息缺少，超出派送范围， 退回改寄件，拒收） 2：知晓类.....
     */
    private Integer alarmTyp;

    /**
     * 预警值:1,2,3,4.... 和alarmName一一对应
     */
    private Integer alarmVal;

    /**
     * 预警级别0~N 从底到高，数字越大，级别越高
     */
    private Integer alarmLevel;

    /**
     * 预警类名称分类名（大类）：处理类；通知类；知晓类；作弊类；超时类；类名称：妥投预警 ；提货预警；派单预警；配送预警；......
     */
    private String alarmName;

    /**
     * 说明描述
     */
    private String descr;

    /**
     * 创建时间
     */
    private String ctime;

    /**
     * 租户id
     */
    private Long tenantId;


}
