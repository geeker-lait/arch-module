package org.arch.alarm.mvc.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * 履约预警消息维度， 执行预警发送的消息 实体
 *
 * @author lait.zhang@gmail.com
 * @date 2021-05-03 9:33 PM
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@TableName("alarm_msg")
public class AlarmMsgEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键|消息id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 通道id
     */
    private Long channelId;

    /**
     * 用户id货账号id
     */
    private Long userId;

    /**
     * 状态 0：成功，1：失败
     */
    private Integer state;

    /**
     * 消息内容
     */
    private String content;

    /**
     * 发送时间
     */
    private Date sendTime;

    /**
     * 创建时间
     */
    private Date ctime;

    /**
     * 租户id
     */
    private Long tenantId;


}
