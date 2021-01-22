package org.arch.payment.sdk.ex;

import org.arch.framework.exception.BaseException;
import org.arch.payment.sdk.PayStatusCode;

public class PayException extends BaseException {

    public PayException(PayStatusCode responseCode, Object[] args, String message) {
        super(responseCode, args, message);
    }

    public PayException(PayStatusCode responseCode, Object[] args, String message, Throwable cause) {
        super(responseCode, args, message, cause);
    }
}
