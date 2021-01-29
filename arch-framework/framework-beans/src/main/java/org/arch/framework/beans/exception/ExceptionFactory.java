package org.arch.framework.beans.exception;

import org.arch.framework.beans.enums.StatusCode;
import org.springframework.stereotype.Component;

/**
 * 异常工厂类
 * 这样可以更加规范，通过ExceptionFactory创建相关的异常
 */
@Component
public class ExceptionFactory {

    public void throwAuthenticationException(StatusCode code, Object... args) {
        throw new AuthenticationException(code,args);
    }

}
