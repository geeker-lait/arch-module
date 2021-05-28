//package com.unichain.pay.yeepay.domain;
//
//
//import cn.hutool.core.bean.BeanUtil;
//import com.unichain.pay.core.PayParam;
//import com.unichain.pay.core.PayRequest;
//import lombok.Data;
//
//import java.util.Map;
//
///**
// * 签约申请验证 请求
// *
// * @date
// */
//@Data
//public class BankcardBindVerifyParam implements PayParam {
//
//    //商户编号 merchantno string(32) Y 易宝平台为商户分配的唯一标识
//    private String merchantno;
//
//    //绑卡请求号 requestno string(32) Y 商户生成的唯一绑卡请求号
//    private String requestno;
//
//    //短验码 validatecode string(8) Y 短信或语音验证码，6 位数字
//    private String validatecode;
//
//
//    @Override
//    public void setSign(String prikeyvalue) {
//
//    }
//
//    @Override
//    public PayParam convert(Map<String, Object> map, PayRequest payRequest) {
//        BankcardBindVerifyParam bean = BeanUtil.mapToBean(map, BankcardBindVerifyParam.class, true);
//        BeanUtil.copyProperties(bean, this);
//        return this;
//    }
//
//    @Override
//    public PayParam encrypt(String encryptKey) {
//        return this;
//    }
//}
