package org.arch.payment.biz;

import org.arch.payment.api.PayChannelAccountDto;
import org.arch.payment.rest.PayRest;
import org.arch.payment.sdk.RefundResult;
import org.arch.payment.sdk.order.impl.QueryOrder;
import org.arch.payment.sdk.order.impl.RefundOrder;
import org.arch.payment.sdk.order.impl.TransferOrder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;


public abstract class PayBiz implements PayRest {
    @Override
    public Map<String, Object> add(PayChannelAccountDto payChannelAccountDto) {
        return null;
    }

    @Override
    public String toPay(HttpServletRequest request, Integer payId, String transactionType, String bankType, BigDecimal price) {
        return null;
    }

    @Override
    public String toWxPay(HttpServletRequest request) {
        return null;
    }

    @Override
    public Map toPay(Integer payId, String openid, BigDecimal price) {
        return null;
    }

    @Override
    public Map<String, Object> getOrderInfo(Integer payId, String transactionType, BigDecimal price) {
        return null;
    }

    @Override
    public Map<String, Object> microPay(Integer payId, String transactionType, BigDecimal price, String authCode) {
        return null;
    }

    @Override
    public byte[] toWxQrPay(Integer payId, String transactionType, BigDecimal price) throws IOException {
        return new byte[0];
    }

    @Override
    public String getQrPay(Integer payId, String transactionType, BigDecimal price) throws IOException {
        return null;
    }

    @Override
    public byte[] toWxAliQrPay(Integer wxPayId, Integer aliPayId, BigDecimal price, HttpServletRequest request) throws IOException {
        return new byte[0];
    }

    @Override
    public String toWxAliPay(Integer wxPayId, Integer aliPayId, BigDecimal price, HttpServletRequest request) throws IOException {
        return null;
    }

    @Override
    public String payBackOne(HttpServletRequest request, Integer payId) throws IOException {
        return null;
    }

    @Override
    public String payBack(HttpServletRequest request, Integer payId) throws IOException {
        return null;
    }

    @Override
    public Map<String, Object> query(QueryOrder order) {
        return null;
    }

    @Override
    public Map<String, Object> close(QueryOrder order) {
        return null;
    }

    @Override
    public RefundResult refund(Integer payId, RefundOrder order) {
        return null;
    }

    @Override
    public Map<String, Object> refundquery(Integer payId, RefundOrder order) {
        return null;
    }

    @Override
    public Object downloadbill(QueryOrder order) {
        return null;
    }

    @Override
    public Map<String, Object> transfer(int payId, TransferOrder order) {
        return null;
    }

    @Override
    public Map<String, Object> transferQuery(int payId, String outNo, String tradeNo) {
        return null;
    }
}
