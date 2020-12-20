package org.arch.framework.exception;

/**
 * 参数异常
 * 在处理业务过程中校验参数出现错误, 可以抛出该异常
 * 编写公共代码（如工具类）时，对传入参数检查不通过时，可以抛出该异常
 */
public class ArgumentException extends BaseException {

    private static final long serialVersionUID = 1L;

    public ArgumentException(StatusCode responseCode, Object[] args, String message) {
        super(responseCode, args, message);
    }

    public ArgumentException(StatusCode responseCode, Object[] args, String message, Throwable cause) {
        super(responseCode, args, message, cause);
    }
}
