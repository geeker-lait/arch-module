package org.arch.project.alarm;

import lombok.Getter;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date :
 */
public enum RegDescr {
    FINISHED_BEFORRE_PICKING("finishedBeforePickingComputer","捡货提前抛单"),
    STORE_CHEAT("storeCheatComputer","门店作弊"),
    ;

    @Getter
    private String regKey;
    @Getter
    private String regName;

    RegDescr(String regKey, String regName) {
        this.regKey = regKey;
        this.regName = regName;
    }
}
