package com.unichain.pay.huifu.exception;

import lombok.Getter;

/**
 * @Auther: zorro
 * @Date: 2019/1/13 22:31
 * @Description:
 */
public enum PayErrorCode implements Code {

    PAY_DB_ERROR("10000", "服务器开小差"),
    PAY_CHANNEL_UNEXIST("10001", "支付渠道不存在"),
    PAY_PARAM_ERROR("10002", "参数异常"),
    PAY_KQ_CARD_REQUEST_ERROR("10003", "请求绑卡异常"),
    PAY_READ_ERROR("10004", "读取内容出错"),
    PAY_REQUEST_ERROR("10005", "请求失败"),
    PAY_UNBIND_CHANNEL_ERROR("10006", "解绑渠道不存在,请手动解绑"),
    PAY_ORDER_EXIST_ERROR("10007", "订单未支付成功"),
    PAY_BANK_CARD_EXIST_ERROR("10008", "绑卡信息不存在"),
    PAY_THIRD_ORDER_NO_ERROR("10009", "支付第三方返回单号不存在"),
    PAY_REFUND_ERROR("10010", "退款失败,请手动退款解绑"),
    PAY_BANKCARD_ERROR("10011", "银行卡号异常"),
    WITHHOLD_ORDER_ERROR("10012", "代扣订单号不存在"),
    PAY_SIGN_ERROR("10013", "验签失败"),
    PAY_APP_ERROR("10014", "应用不存在"),
    REMIT_ORDER_EXIST("10015", "放款订单已提交过"),
    REMIT_ORDER_NO_EXIST("10016", "放款订单号不存在"),
    RRFUND_ORDER_NO_EXIST("10017", "退款返回订单号不存在"),
    PAY_REQUEST_ERROR_ERROR("10018", "支付请求异常"),
    CHANNEL_NO_EXIST("10019", "渠道不存在"),
    PAY_COMPANY_NO_EXIST("10020", "对应主体不存在"),
    PAY_ACTIVE_API_NO_EXIST("10021", "支付渠道的路由不存在"),
    PAY_WAIT_PAY_NO_EXIST("10022", "暂无待支付的订单");

    @Getter
    private String code;
    @Getter
    private String message;
    PayErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }


}
