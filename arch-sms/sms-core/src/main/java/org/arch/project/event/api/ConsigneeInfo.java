package org.arch.project.event.api;

import lombok.Data;

/**
 * @author lait.zhang@gmail.com
 * @Tel 15801818092
 * @date 11/16/2020 5:05 PM
 * @description: 收货人信息
 */
@Data
public class ConsigneeInfo {
    private Long userId;
    // 收件人
    private String userName;
    // 手机号
    private String phoneNo;
    // 省
    private String province;
    private String city;
    private String district;
    private Long address;
}
