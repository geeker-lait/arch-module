package org.arch.framework.beans.exception.assertion;

import org.arch.framework.beans.exception.ArgumentException;
import org.arch.framework.beans.exception.BaseException;
import org.arch.framework.beans.enums.StatusCode;

import java.text.MessageFormat;

/**
 * 公共异常信息
 */
public interface CommonExceptionAssert extends StatusCode, Assert {

    @Override
    default BaseException newException(Object... args) {
        String msg = MessageFormat.format(this.getDescr(), args);

        return new ArgumentException(this, args, msg);
    }

    @Override
    default BaseException newException(Throwable t, Object... args) {
        String msg = MessageFormat.format(this.getDescr(), args);

        return new ArgumentException(this, args, msg, t);
    }

}
