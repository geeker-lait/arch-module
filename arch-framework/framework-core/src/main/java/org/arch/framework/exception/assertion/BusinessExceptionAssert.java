package org.arch.framework.exception.assertion;

import org.arch.framework.exception.BaseException;
import org.arch.framework.exception.BusinessException;
import org.arch.framework.exception.StatusCode;

import java.text.MessageFormat;

/**
 * 业务异常断言
 */
public interface BusinessExceptionAssert extends StatusCode, Assert {

    @Override
    default BaseException newException(Object... args) {
        String msg = MessageFormat.format(this.getMessage(), args);

        return new BusinessException(this, args, msg);
    }

    @Override
    default BaseException newException(Throwable t, Object... args) {
        String msg = MessageFormat.format(this.getMessage(), args);

        return new BusinessException(this, args, msg, t);
    }

}
