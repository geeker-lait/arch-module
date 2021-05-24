package org.arch.workflow.common.exception;

/**
 * 对象没有找到异常
 *
 * @author lait.zhang@gmail.com
 * @date 2018年4月2日
 */
class ObjectNotFoundException extends BaseException {

    private static final long serialVersionUID = 1L;

    ObjectNotFoundException(String ret, String msg) {
        super(ret, msg);
    }

}
