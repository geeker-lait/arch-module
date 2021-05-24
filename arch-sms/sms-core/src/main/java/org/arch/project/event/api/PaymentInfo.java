package org.arch.project.event.api;

import lombok.Data;

import java.io.Serializable;

/**
 * @author lait.zhang@gmail.com
 * @Tel 15801818092
 * @date 11/16/2020 5:08 PM
 * @description: 支付信息(已支付:先款后货；未支付:货到付款)
 */
@Data
public class PaymentInfo implements Serializable {

    // 是否已经支付
    private Boolean paymented;
    // 是否货到付款
    private Boolean getPay;

}
