package org.arch.payment.sdk.request;

import lombok.Data;

/**
 * @author lait.zhang@gmail.com
 * @description: 绑卡四要素
 * @weixin PN15855012581
 * @date 12/30/2020 8:59 PM
 */
@Data
public class BindCardElements {
    // 用户姓名
    private String realName;
    // 预留手机号
    private String phoneNum;
    // 英航卡号
    private String bankcard;
    // 身份证号
    private String idcard;
}
