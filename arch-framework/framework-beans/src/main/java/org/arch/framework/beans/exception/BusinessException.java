package org.arch.framework.beans.exception;

import org.arch.framework.beans.enums.StatusCode;

/**
 * 业务异常
 * 业务处理时，出现异常，可以抛出该异常
 */
public class BusinessException extends BaseException {

    private static final long serialVersionUID = 1L;

    public BusinessException(String message) {
        super(message);
    }
    public BusinessException(StatusCode responseCode) {
        super(responseCode);
    }

    public BusinessException(StatusCode responseCode, Object[] args, String message) {
        super(responseCode, args, message);
    }

    public BusinessException(StatusCode responseCode, Object[] args, String message, Throwable cause) {
        super(responseCode, args, message, cause);
    }
}
