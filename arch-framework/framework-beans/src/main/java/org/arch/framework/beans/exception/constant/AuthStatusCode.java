package org.arch.framework.beans.exception.constant;

import org.arch.framework.beans.enums.StatusCode;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date 12/11/2020 3:30 PM
 */
public enum AuthStatusCode implements StatusCode {
    UNAUTHORIZED(401, "暂未登录或token已经过期"),
    FORBIDDEN(403, "没有相关权限"),
    ;


    private int code;
    private String message;

    AuthStatusCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public int getCode() {
        return 0;
    }

    @Override
    public String getDescr() {
        return null;
    }
}
