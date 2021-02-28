package org.arch.framework.beans.exception;

import org.arch.framework.beans.enums.StatusCode;

/**
 * 基础异常类，所有自定义异常类都需要继承本类
 */
public class BaseException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * 返回码
     */
    protected StatusCode responseCode;
    /**
     * 异常消息参数
     */
    protected Object[] args;

    public BaseException(String msg) {
        super(msg);
    }

    public BaseException(StatusCode responseCode) {
        super(responseCode.getDescr());
        this.responseCode = responseCode;
    }

    public BaseException(int code, String msg) {
        super(msg);
        this.responseCode = new StatusCode() {
            @Override
            public int getCode() {
                return code;
            }

            @Override
            public String getDescr() {
                return msg;
            }
        };
    }

    public BaseException(StatusCode responseCode, Object[] args, String message) {
        super(message);
        this.responseCode = responseCode;
        this.args = args;
    }

    public BaseException(StatusCode responseCode, Object[] args, String message, Throwable cause) {
        super(message, cause);
        this.responseCode = responseCode;
        this.args = args;
    }



    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public StatusCode getResponseCode() {
        return responseCode;
    }

    public Object[] getArgs() {
        return args;
    }

}
