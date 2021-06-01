package org.arch.sms.api.enums;

import lombok.Getter;

/**
 * 验证码类型
 */
public enum CodeTyp {

    LOGIN_VERIFY_CODE("登录验证码"),
    REGIST_VERIFY_CODE("注册验证码"),
    ;

    @Getter
    String descr;

    CodeTyp(String descr) {
        this.descr = descr;
    }
}
