package org.arch.project.event.api;

import lombok.Getter;

/**
 * @author lait.zhang@gmail.com
 * @Tel 15801818092
 * @date 11/16/2020 8:25 PM
 * @description: 履约阶段
 */
public enum  FulfilPhase {

    // 捡货
    PICK("store-inner-work:pick"),
    // 打包
    PACK("store-inner-work:pack"),
    // 出单
    ISSUING("store-inner-work:issuing"),



    //
    ACCEPT("store-outer-work:accept"),

    COLLECTION("store-outer-work:collection"),

    DISTRIBUTE("store-outer-work:distribute"),


    // 派件
    DELIVERY("tailend-work:delivery"),
    // 收货
    RECEIVE("tailend-work:receive"),
    // 退货
    REFUND("tailend-work:refund");

    @Getter
    private String val;

    FulfilPhase(String val) {
        this.val = val;
    }
}
