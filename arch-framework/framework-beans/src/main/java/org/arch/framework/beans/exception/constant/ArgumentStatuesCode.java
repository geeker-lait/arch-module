package org.arch.framework.beans.exception.constant;

import org.arch.framework.beans.exception.assertion.CommonExceptionAssert;

/**
 * 参数校验异常返回结果
 */
public enum ArgumentStatuesCode implements CommonExceptionAssert {
    /**
     * 绑定参数校验异常
     */
    VALID_ERROR(6000, "参数校验异常"),
    VALIDATE_FAILED(6004, "参数检验失败"),
    ;

    /**
     * 返回码
     */
    private final int code;
    /**
     * 返回消息
     */
    private final String message;

    public int getCode() {
        return code;
    }

    public String getDescr() {
        return message;
    }

    ArgumentStatuesCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

}
