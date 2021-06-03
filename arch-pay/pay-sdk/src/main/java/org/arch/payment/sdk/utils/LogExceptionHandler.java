package org.arch.payment.sdk.utils;

import org.arch.payment.sdk.ex.PayErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogExceptionHandler implements PayErrorExceptionHandler {

    protected final Logger LOGGER = LoggerFactory.getLogger(PayErrorExceptionHandler.class);

    @Override
    public void handle(PayErrorException e) {

        LOGGER.error("Error happens", e);

    }

}
