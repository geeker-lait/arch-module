package org.arch.marketing.core;

import lombok.Getter;

/**
 * @author lait.zhang@gmail.com
 * @description: 1:获客拉新、2:下单转化、3:提高客单、4:复购留存、5:....(数据字典获取)
 * @weixin PN15855012581
 * @date 1/13/2021 8:10 PM
 */
public enum MarketingTyp {
    LA_XIN(1),
    XIA_DAN(2),
    KE_DAN(3),
    CUN_LIU(4),
    ;

    @Getter
    private int val;

    MarketingTyp(int val) {
        this.val = val;
    }
}
