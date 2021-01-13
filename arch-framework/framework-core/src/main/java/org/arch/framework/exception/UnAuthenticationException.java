package org.arch.framework.exception;

/**
 * 未登录异常
 * @author YongWu zheng
 * @since 2021.1.3 17:10
 */
public class UnAuthenticationException extends BaseException {

    private static final long serialVersionUID = -3269140788552978763L;

    public UnAuthenticationException(StatusCode responseCode) {
        super(responseCode);
    }

    public UnAuthenticationException(int code, String message) {
        super(code, message);
    }
}
