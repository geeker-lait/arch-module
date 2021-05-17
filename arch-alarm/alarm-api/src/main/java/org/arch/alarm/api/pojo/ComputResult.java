package org.arch.alarm.api.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date 5/1/2021 11:51 PM
 */
@Data
public class ComputResult implements Serializable {
    // 计算结果
    private Object result;
    private boolean success;
    private String msg;
    // 规则id,用于查询alarm_notice
    private Long regId;

}
