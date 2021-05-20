package org.arch.workflow.common.exception;

/**
 * 自定义异常
 *
 * @author lait.zhang@gmail.com
 */
class BaseException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private String ret;

    BaseException(String ret, String msg) {
        super(msg);
        this.ret = ret;
    }

    BaseException(String ret, String msg, Throwable cause) {
        super(msg, cause);
        this.ret = ret;
    }

    String getRet() {
        return ret;
    }
}
