package org.arch.alarm.api.enums;

import lombok.Getter;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date 5/11/2021 9:54 AM
 */
public enum UserTyp {
    USER(0),
    GROUP(1);
    @Getter
    private Integer typ;

    UserTyp(Integer typ) {
        this.typ = typ;
    }
}
