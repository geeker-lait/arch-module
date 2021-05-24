package org.arch.alarm.biz.config.properties;

import lombok.Data;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date 5/10/2021 8:06 PM
 */
@Data
public class ChannelProperties {
    // 通道名称
    private String name;
    // 通道签名
    private String sign;
    // 仓店号
    private String storeNo;
    // 通道码
    private String code;
    // 通道值
    private Integer val;
    // 通道url
    private String url;
    // 通道回调URL
    private String callback;
    // 密钥
    private String seckey;
    // 应用key
    private String appkey;
}
