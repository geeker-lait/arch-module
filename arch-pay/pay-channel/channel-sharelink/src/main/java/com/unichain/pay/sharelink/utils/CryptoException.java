package com.unichain.pay.sharelink.utils;

public class CryptoException extends RuntimeException {

    private static final long serialVersionUID = 368277451733324220L;

    public CryptoException(String message) {
        super(message);
    }

    public CryptoException(String message, Throwable cause) {
        super(message, cause);
    }
}
