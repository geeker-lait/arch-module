package org.arch.alarm.api.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date 5/8/2021 10:52 AM
 */
@Data
public class AlarmNoticeDto implements Serializable {
    /**
     * 主键
     */
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
