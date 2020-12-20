package com.unichain.pay.huifu.exception;


/**
 * @version 1.0
 * @date 2017年11月8日
 */
public class PayErrException extends RuntimeException {
    /**
     *
     */
    private static final long serialVersionUID = 3658216696054712345L;
    /**
     * Error message.
     */
    public String message;
    private String code;

    public PayErrException() {
    }

    public PayErrException(String message, String code) {
        super(message);
        this.code = code;
    }

    public PayErrException(PayErrorCode payErrorCode) {
        super(payErrorCode.getMessage());
        this.code = code;
    }

    public PayErrException(String message) {
        super(message);
    }

    public PayErrException(String message, Throwable e) {
        super("支付中心异常 : " + message, e);
    }

    public PayErrException(Throwable e) {
        super(e);
    }

    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
