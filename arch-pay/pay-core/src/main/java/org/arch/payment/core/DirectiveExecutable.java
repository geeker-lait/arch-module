//package org.arch.payment.core;
//
//
//import org.arch.payment.core.entity.PayChannelDirectiveRecord;
//import org.arch.payment.sdk.*;
//
///**
// * 支付指令
// *
// * @param <DR>
// */
//public interface DirectiveExecutable<PP extends PayParam,DR extends DirectiveResponse> {
//    /**
//     * 执行支付指令
//     *
//     * @param payConfigurable
//     * @param payRequest
//     * @return
//     */
//    PayResponse<DR> exec(PayRequest payRequest, PayConfigurable payConfigurable);
//
//    /**
//     * 构建支付参数
//     * @param payRequest
//     * @return
//     */
//    PP buildPayParams(PayRequest payRequest, PayConfigurable payConfigurable);
//
//    /**
//     * 获取指令码
//     *
//     * @return
//     */
//    String getDirectiveCode();
//
//    /**
//     * 记录
//     * @param payRequest
//     * @param directiveResponse
//     */
//    void record(PayRequest payRequest, PP payParams, DR directiveResponse);
//
//
//    /**
//     * 转码
//     * @param payResponse
//     * @return
//     */
//    void transcoding(PayResponse<DR> payResponse);
//
//
//
//    /**
//     * 创建默认记录对象
//     * @param payRequest
//     * @return
//     */
//    default PayChannelDirectiveRecord createChannelDirectiveRecord(PayRequest payRequest) {
//        PayChannelDirectiveRecord save = new PayChannelDirectiveRecord();
//        //1 设置appId
//        save.setAppId(payRequest.getAppId());
//        //2 设置账户ID
//        save.setAccountId(payRequest.getAccountId());
//        //3 设置用户ID
//        save.setUserId(payRequest.getUserId());
//        //4 设置商户ID
//        save.setCompanyId(payRequest.getMerchantId());
//        //5 设置通道ID
//        save.setChannelId(payRequest.getChannelId());
//        //6 设置具体指令
//        save.setDirectiveCode(getDirectiveCode());
//        //7 设置银行卡号
//        save.setBankcard(payRequest.getBankcard());
//        //8 设置支付ID
//        save.setPaymentId(payRequest.getPaymentId());
//        //9 设置设备ID
//        save.setPhone(payRequest.getDeviceId());
//        //10 银行code
//        //save.setBankCode();
//        //发布生产
//        return save;
//    }
//
//}
