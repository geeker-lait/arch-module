//package com.unichain.pay.sharelink.domain;
//
//import cn.hutool.core.date.DateUtil;
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//import com.alibaba.fastjson.serializer.SerializerFeature;
////mport com.unichain.pay.channel.mfe88.utils.Base64;
//import com.unichain.pay.core.PayParam;
//import com.unichain.pay.core.PayRequest;
//import com.unichain.pay.core.util.AmountUtils;
//import com.unichain.pay.core.util.BankCardNoUtil;
//import com.unichain.pay.core.util.BankInfoUtil;
//import lombok.Data;
//import org.springframework.beans.BeanUtils;
//
//import java.io.UnsupportedEncodingException;
//import java.math.BigDecimal;
//import java.text.DateFormat;
//import java.text.SimpleDateFormat;
//import java.util.Calendar;
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * @Author lait.zhang@gmail.com
// * @Tel 15801818092
// * @Date 12/3/2019
// * @Description ${Description}
// */
//@Data
//public class PayForAnotherParam implements PayParam {
//
//    //版本
//    private String version = "1.0.1";
//    //商户标识
//    private String merchantId;
//    //商户订单号
//    private String orderId;
//    //请求时间
//    private String reqDate = DateUtil.date().toString("yyyyMMdd HH:mm:ss");
//    //金额
//    private BigDecimal amount;
//    //收款人姓名
//    private String name;
//    //收款机构号
//    private String organCode;
//    //收款人账号
//    private String account;
//    //收款人账户类型
//    private String accountType = "90";
//    //收款人证件类型
//    private String idType="10";
//    //收款人证件号码
//    private String idCode;
//    //业务类型分类
//    private String orderBizzType = "100001";
//    //签名信息
//    private String sign;
//
//
//    @Override
//    public PayParam convert(Map<String, Object> map, PayRequest payRequest) {
//        // 首先统一处理相同字段
//        PayForAnotherParam payForAnotherParam = JSON.parseObject(JSON.toJSONString(map), PayForAnotherParam.class);
//        BeanUtils.copyProperties(payForAnotherParam, this);
//
//        Object bankcard = map.get("bankcard");
//        if (bankcard != null) {
//            this.account = bankcard.toString();
//        } else {
//            throw new RuntimeException("银行卡号为空!");
//        }
//        this.orderId = payRequest.getPaymentId();
//        this.organCode = BankInfoUtil.getOrganCode(account).getString("organCode");
//        this.name = String.valueOf(map.get("userName"));
//        this.idCode = String.valueOf(map.get("idCard"));
//
//        return this;
//    }
//
//    @Override
//    public PayParam encrypt(String encryptKey) {
//        /*try {
//            this.accountNo = Base64.encode(this.accountNo);
//            this.accountName = Base64.encode(this.accountName);
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }*/
//        return this;
//    }
//
//    public String toJsonString() {
//        return JSONObject.toJSONString(this.encrypt(null), SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue);
//    }
//}
