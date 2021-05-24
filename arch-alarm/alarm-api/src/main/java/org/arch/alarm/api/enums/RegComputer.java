package org.arch.alarm.api.enums;

import lombok.Getter;

/**
 * @author lait.zhang@gmail.com
 * @description: 规则计算器
 * @weixin PN15855012581
 * @date :
 */
public enum RegComputer {
    FINISHED_BEFORRE_PICKING_COMPUTER("finishedBeforePickingComputer", "捡货提前抛单规则计算器"),
    STORE_CHEAT_COMPUTER("storeCheatComputer", "门店作弊规则计算器"),
    STORE_DISPATCH_ORDER_TIMEOUT_COMPUTER("storeDispatchOrderTimeoutComputer", "门店派单超时计算器"),
    THIRD_TRANSPORT_RECEIVE_ORDER_TIMEOUT("thirdTransportReceiveOrderTimeoutComputer", "三方运力接单超时计算器"),
    EXPRESSION_COMPUTER("expressionComputer", "表达式计算器"),
    ;

    @Getter
    private String computerName;
    @Getter
    private String computerDescr;

    RegComputer(String computerName, String computerDescr) {
        this.computerName = computerName;
        this.computerDescr = computerDescr;
    }
}
