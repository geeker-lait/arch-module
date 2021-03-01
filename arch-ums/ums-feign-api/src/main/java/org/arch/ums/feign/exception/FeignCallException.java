package org.arch.ums.feign.exception;

import lombok.extern.slf4j.Slf4j;
import org.arch.framework.beans.enums.StatusCode;
import org.arch.framework.beans.exception.BusinessException;

/**
 * feign远程调用异常
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.2.28 13:46
 */
@Slf4j
public class FeignCallException extends BusinessException {

    private static final long serialVersionUID = -866038787999317767L;


    public FeignCallException(String message) {
        super(message);
    }

    public FeignCallException(StatusCode responseCode) {
        super(responseCode);
    }

    public FeignCallException(StatusCode responseCode, Object[] args, String message) {
        super(responseCode, args, message);
    }

    public FeignCallException(StatusCode responseCode, Object[] args, String message, Throwable cause) {
        super(responseCode, args, message, cause);
    }
}