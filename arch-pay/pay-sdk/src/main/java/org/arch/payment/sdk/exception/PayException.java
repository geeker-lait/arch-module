package org.arch.payment.sdk.exception;
//
//import org.arch.framework.beans.exception.BaseException;
//import org.arch.payment.sdk.enums.PayCodeDescr;
//
//public class PayException extends BaseException {
//
//    public PayException(PayCodeDescr responseCode, Object[] args, String message) {
//        super(responseCode, args, message);
//    }
//
//    public PayException(PayCodeDescr responseCode, Object[] args, String message, Throwable cause) {
//        super(responseCode, args, message, cause);
//    }
//}

import org.arch.payment.sdk.PayError;

public class PayException implements PayError {
    private String errorCode;
    private String errorMsg;
    private String content;

    @Override
    public String getErrorCode() {
        return errorCode;
    }

    @Override
    public String getErrorMsg() {
        return errorMsg;
    }

    public PayException(String errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public PayException(String errorCode, String errorMsg, String content) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
        this.content = content;
    }

    @Override
    public String getString() {
        return "支付错误: errcode=" + errorCode + ", errmsg=" + errorMsg + (null == content ? "" : "\n content:" + content);
    }
}