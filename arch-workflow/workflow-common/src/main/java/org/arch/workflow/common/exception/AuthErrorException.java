package org.arch.workflow.common.exception;

/**
 * 授权验证失败异常
 *
 * @author lait.zhang@gmail.com
 * @date 2018年4月2日
 */
class AuthErrorException extends BaseException {

    private static final long serialVersionUID = 1L;

    public AuthErrorException(String ret, String msg) {
        super(ret, msg);
    }

    public AuthErrorException(String ret, String msg, Throwable cause) {
        super(ret, msg, cause);
    }

}
