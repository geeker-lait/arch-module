package org.arch.payment.sdk.exception;

import org.arch.payment.sdk.PayError;

public class PayErrorException extends RuntimeException  {

    private PayError error;

    public PayErrorException(PayError error) {
        super(error.getString());
        this.error = error;
    }


    public PayError getPayError() {
        return error;
    }
}