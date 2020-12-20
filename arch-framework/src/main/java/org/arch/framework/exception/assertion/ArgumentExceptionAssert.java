package org.arch.framework.exception.assertion;

import org.arch.framework.exception.ArgumentException;
import org.arch.framework.exception.BaseException;
import org.arch.framework.exception.StatusCode;

import java.text.MessageFormat;

/**
 *
 */
public interface ArgumentExceptionAssert extends StatusCode, Assert {

    @Override
    default BaseException newException(Object... args) {
        String msg = MessageFormat.format(this.getMessage(), args);

        return new ArgumentException(this, args, msg);
    }

    @Override
    default BaseException newException(Throwable t, Object... args) {
        String msg = MessageFormat.format(this.getMessage(), args);

        return new ArgumentException(this, args, msg, t);
    }

}
