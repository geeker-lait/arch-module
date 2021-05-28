//package com.unichain.pay.yeepay.domain;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//import com.alibaba.fastjson.serializer.SerializerFeature;
////import com.unichain.pay.channel.mfe88.utils.Base64;
//import com.unichain.pay.core.PayParam;
//import com.unichain.pay.core.PayRequest;
//import com.unichain.pay.core.util.AmountUtils;
//import com.unichain.pay.core.util.BankCardNoUtil;
//import com.unichain.pay.core.util.BankInfoUtil;
//import lombok.Data;
//import org.springframework.beans.BeanUtils;
//
//import java.io.UnsupportedEncodingException;
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
//    //接口名称	20	固定值 : payForAnotherOne	是
//    private String service = "payForAnotherOne";
//    //商户编号	50	商户在的唯一标识	是
//    private String merchantNo = "CX0002114";
//    //商户订单号	50	商户系统产生的订单号	是
//    private String orderNo;
//    //	网关版本	10	固定值：V3.0。	是
//    private String version = "V3.0";
//    //	签名版本	10	固定值：V3.0	是
//    private String signVersion = "V3.0";
//    //	风控版本	10	固定值：V3.0	是
//    private String riskVersion = "V3.0";
//    //	对公对私	1	1=对公；2=对私	是
//    private String accountProp = "2";
//    //	银行账号	32	银行卡或存折号码；内容用base64加密	是
//    private String accountNo;
//    //	账户名称	60	银行卡或存折上的所有人姓名。内容用base64加密	是
//    private String accountName;
//    //	银行通用名称	60	银行名称：如：中国人民银行	是
//    private String bankGenneralName;
//    //	开户行名称	60	开户行详细名称，也叫网点，如：金牛区支行	是
//    private String bankName;
//    //	开户行号	60	对方开户行行号，对方账户对应的支行行号：如：ICBC 参考：5.2.1	是
//    private String bankCode;
//    //	货币类型	3	人民币：CNY；港元:HKD；美元USD；目前只支持人民币CNY	是
//    private String currency = "CNY";
//    //	开户行所在省	20	带“省”、“自治区”，如广东省，广西省，内蒙古自治区等	是
//    private String bankProvcince;
//    //	开户行所在市	20	带市，如上海市、成都市等	是
//    private String bankCity;
//    //	金额	16	整数，单位分	是
//    private String orderAmount;
//    //	手机号	13	不带括号，减号	否
//    private String tel;
//    //	打款原因	256	用来注明该笔款项的用途或其它内容等	否
//    private String cause;
//    //	订单时间	14	商户订单提交时间，格式：yyyyMMddHHmmss 是
//    private String orderTime;
//
//    //	订单来源	1	订单来源：1网页2手机3pos机4直连	否
//    private String orderSource;
//    //	签名类型	1	签名类型：1: MD5； 2： RSA； 3：CFCA证书	是
//    private String signType = "3";
//    //	商户签名数据	1024	具体参考 4.1	是
//    private String sign;
//    //	服务器接收支付结果的后台地址	256	服务器异步通知商户接口路径，主动调商户接口通知订单支付结果。此参数为空，则将不主动通知商户订单支付结果，商户可以查询订单获取订单状态	否
//    private String notifyUrl;
//
//    public static void main(String[] args) {
//        PayForAnotherParam payForAnotherParam = new PayForAnotherParam();
//        payForAnotherParam.setAccountNo("123123213");
//        payForAnotherParam.setAccountName("lait");
//
//        Map<String, Object> paramMap = new HashMap<>();
//        payForAnotherParam.convert(paramMap, null);
//        System.out.println(payForAnotherParam.toJsonString());
//    }
//
//    @Override
//    public PayParam convert(Map<String, Object> map, PayRequest payRequest) {
//        // 首先统一处理相同字段
//        PayForAnotherParam payForAnotherParam = JSON.parseObject(JSON.toJSONString(map), PayForAnotherParam.class);
//        BeanUtils.copyProperties(payForAnotherParam, this);
//
//        DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
//        Calendar calendar = Calendar.getInstance();
//
//        Object bankcard = map.get("bankcard");
//        if (bankcard != null) {
//            this.accountNo = bankcard.toString();
//        } else {
//            throw new RuntimeException("银行卡号为空!");
//        }
//        // 设置订单号
//
//        try {
//            orderAmount = AmountUtils.changeY2F(this.orderAmount);
//        } catch (Exception e) {
//            throw new RuntimeException("金额转账出错:" + e.getMessage());
//        }
//        this.orderNo = payRequest.getPaymentId();
//        this.bankCode = BankCardNoUtil.getBankCardNo(this.accountNo);
//        String bn = BankInfoUtil.getBankName(this.accountNo);
//        this.bankGenneralName = null != bn ? bn : "未知银行";
//        this.orderTime = df.format(calendar.getTime());
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
