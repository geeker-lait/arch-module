package org.arch.alarm.biz.notifier.weixin;

import lombok.Data;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date 5/12/2021 6:49 PM
 */
@Data
public class WxProperties {
    private String type;
    private String msgtype;
    private String name;
    private String sign;
}
