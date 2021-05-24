package org.arch.alarm.mvc.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * 履约告警规则维度 确定哪些仓店使用那些通知规则，并确定接受预警通知的用户配置 实体
 *
 * @author lait.zhang@gmail.com
 * @date 2021-05-03 9:33 PM
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@TableName("alarm_notice")
public class AlarmNoticeEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 通知模板id
     */
    private Long templateId;

    /**
     * 通道id
     */
    private Long channelId;

    /**
     * 规则id
     */
    private Long regId;

    /**
     * 仓店号
     */
    private String storeNo;

    /**
     * 用户id货账号id
     */
    private Long userId;

    /**
     * 用户类型{1:用户，2：用户组userGroup}
     */
    private Integer userTyp;

    /**
     * job定时器id，结合job 可做定时发送，为空则立刻发送
     */
    private Long jobId;

    /**
     * 创建时间
     */
    private Date ctime;

    /**
     * 租户id
     */
    private Long tenantId;


}
