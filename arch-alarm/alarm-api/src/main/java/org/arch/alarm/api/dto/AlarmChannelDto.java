package org.arch.alarm.api.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date 5/8/2021 5:38 PM
 */
@Data
public class AlarmChannelDto implements Serializable {

    /**
     * 主键
     */
    private Long id;

    /**
     * 仓店号
     */
    private String storeNo;

    /**
     * 通道码，msg，weixin，email，sys
     */
    private String channelCode;

    /**
     * 通道值，1，2，3....
     */
    private Integer channelVal;

    /**
     * 通道名称
     */
    private String channelName;

    /**
     * 通道url
     */
    private String channelUrl;

    /**
     * 回调地址
     */
    private String callbackUrl;

    /**
     * 签名
     */
    private String sign;

    /**
     * 应用id或应用key
     */
    private String appKey;

    /**
     * 密钥
     */
    private String secrtKey;

    /**
     * 扩展属性
     */
    private String extJsonp;
    /**
     * 创建时间
     */
    private Date ctime;

    /**
     * 租户id
     */
    private Long tenantId;
}
