package org.arch.framework.beans.exception.assertion;

import org.arch.framework.beans.exception.BaseException;
import org.arch.framework.beans.exception.BusinessException;
import org.arch.framework.beans.enums.StatusCode;

import java.text.MessageFormat;

/**
 * 业务异常断言
 */
public interface BusinessExceptionAssert extends StatusCode, Assert {

    @Override
    default BaseException newException(Object... args) {
        String msg = MessageFormat.format(this.getDescr(), args);

        return new BusinessException(this, args, msg);
    }

    @Override
    default BaseException newException(Throwable t, Object... args) {
        String msg = MessageFormat.format(this.getDescr(), args);

        return new BusinessException(this, args, msg, t);
    }

}
