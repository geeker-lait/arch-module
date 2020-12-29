package org.arch.payment.core.api;


import com.egzosn.pay.common.exception.PayErrorException;

/**
 * PayErrorExceptionHandler处理器
 */
public interface PayErrorExceptionHandler {

    /**
     * 异常统一处理器
     *
     * @param e 支付异常
     */
    void handle(PayErrorException e);

}
