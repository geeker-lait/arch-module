package org.arch.framework.config;

public class ComponentInitException extends RuntimeException {
    public ComponentInitException() {
    }

    public ComponentInitException(String message) {
        super(message);
    }

    public ComponentInitException(String message, Throwable cause) {
        super(message, cause);
    }

    public ComponentInitException(Throwable cause) {
        super(cause);
    }

    protected ComponentInitException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
