package org.arch.framework.beans.exception;

import org.arch.framework.beans.enums.StatusCode;

/**
 * 未登录异常
 * @author YongWu zheng
 * @since 2021.1.3 17:10
 */
public class AuthenticationException extends BaseException {

    private static final long serialVersionUID = -3269140788552978763L;

    public AuthenticationException(StatusCode responseCode) {
        super(responseCode);
    }

    public AuthenticationException(int code, String message) {
        super(code, message);
    }

    public AuthenticationException(StatusCode code, Object[] args) {
        super(code);
    }
}
