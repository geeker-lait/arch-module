package org.arch.framework.beans.exception.assertion;

import org.arch.framework.beans.exception.ArgumentException;
import org.arch.framework.beans.exception.BaseException;
import org.arch.framework.beans.enums.StatusCode;

import java.text.MessageFormat;

/**
 *
 */
public interface ArgumentExceptionAssert extends StatusCode, Assert {

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
