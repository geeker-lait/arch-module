package org.arch.alarm.api.pojo;

import lombok.Data;

import java.io.Serializable;


@Data
public class AlarmRequest implements Serializable {

    private static final long serialVersionUID = -3260633526583757369L;

    /**
     * 告警类型 必填 groupchat
     */
    private String type;

    /**
     * 告警消息类型 必填 markdown
     */
    private String msgtype;

    /**
     * 告警组名称 必填
     */
    private String name;

    /**
     * 告警内容 必填(做好格式化(换行什么的)，方便观察告警内容)
     */
    private String message;

    /**
     * 备注 非必填
     */
    private String remark;

    /**
     * 签名 必填
     */
    private String sign;
}
