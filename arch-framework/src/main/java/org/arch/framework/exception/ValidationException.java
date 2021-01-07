package org.arch.framework.exception;

/**
 * 校验异常
 * 调用接口时，参数格式不合法可以抛出该异常
 */
public class ValidationException extends BaseException {

    private static final long serialVersionUID = 1L;

    public ValidationException(StatusCode responseCode) {
        super(responseCode);
    }

    public ValidationException(StatusCode responseCode, Object[] args, String message) {
        super(responseCode, args, message);
    }

    public ValidationException(StatusCode responseCode, Object[] args, String message, Throwable cause) {
        super(responseCode, args, message, cause);
    }
}
