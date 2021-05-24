package org.arch.workflow.common.exception;

/**
 * 冲突异常
 *
 * @author lait.zhang@gmail.com
 * @date 2018年4月2日
 */
class ConflictException extends BaseException {

    private static final long serialVersionUID = 1L;

    ConflictException(String ret, String msg) {
        super(ret, msg);
    }

}
