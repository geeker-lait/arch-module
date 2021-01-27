package org.arch.framework.beans.exception.constant;

import org.arch.framework.beans.enums.StatusCode;

/**
 * 枚举了一些常用API操作码
 */
public enum ResponseStatusCode implements StatusCode {
    SUCCESS(200, "操作成功"),
    FAILED(500, "操作失败"),
    VALIDATE_FAILED(404, "参数检验失败"),
    UNAUTHORIZED(401, "暂未登录或token已经过期"),
    FORBIDDEN(403, "没有相关权限");
    private int code;
    private String message;

    ResponseStatusCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getDescr() {
        return message;
    }


}
