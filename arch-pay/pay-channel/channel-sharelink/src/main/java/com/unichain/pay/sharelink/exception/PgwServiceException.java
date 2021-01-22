package com.unichain.pay.sharelink.exception;

public class PgwServiceException extends Exception {
    private static final long serialVersionUID = 1L;
    private String code;
    private String msg;
    private String shortCode;


    public PgwServiceException() {

    }

    public PgwServiceException(String code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
        if (null != code && !code.equals("") && code.length() > 4) {
            this.shortCode = code.substring(code.length() - 4);
        }
    }

    public PgwServiceException(String code) {
        super(code, null);
    }

    public String getShortCode() {
        return shortCode;
    }

    public void setShortCode(String shortCode) {
        this.shortCode = shortCode;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
