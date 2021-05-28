//package com.unichain.pay.yeepay.domain;
//
//import cn.hutool.core.date.DateUtil;
//import com.unichain.pay.core.PayParam;
//import com.unichain.pay.core.PayRequest;
//import com.unichain.pay.core.util.BankCardNoUtil;
//import lombok.Data;
//
//import java.util.Map;
//
//@Data
//public class YeepayPrePayParam implements PayParam {
//
//    /**
//     * 商户编号
//     */
//    private String merchantno;
//    /**
//     * 商户生成请求号（支付单号）
//     */
//    private String requestno;
//    /**
//     * 用户标识 商户生成的用户唯一标识
//     */
//    private String identityid;
//    /**
//     * 用户标识类型
//     * MAC：网卡地址
//     * IMEI：国际移动设备标识
//     * ID_CARD：用户身份证号
//     * PHONE：手机号
//     * EMAIL：邮箱
//     * USER_ID：用户 id
//     * AGREEMENT_NO：用户纸质订单协议号
//     */
//    private String identitytype;
//    /**
//     * 银行卡号
//     */
//    private String cardno;
//    /**
//     * 证件号
//     */
//    private String idcardno;
//    /**
//     * 证件类型 固定值ID：身份证
//     */
//    private String idcardtype = "ID";
//    /**
//     * 用户姓名
//     */
//    private String username;
//    /**
//     * 手机号
//     */
//    private String phone;
//    /**
//     * 还款金额额 单位：元,精确到两位小数大于等于0.01
//     */
//    private String amount;
//
//    /**
//     * 鉴权类型
//     * COMMON_FOUR(验四)
//     * （注：商户在易宝需开通拥有鉴权验四的鉴权类型）
//     */
//    private String authtype = "COMMON_FOUR";
//
//    /**
//     * 是否发短信
//     * 允许商户指定此次鉴权是否需要短验
//     * （注：商户在易宝需开通拥有鉴权发短验和
//     * 鉴权不发短验的鉴权类型，且开通无短验绑
//     * 卡的接口权限）
//     */
//    private String issms = "true";
//
//    /**
//     * 建议短验发送类型
//     * 参数 issms 为“true” 即发送短验时考虑此参数
//     * MESSAGE：
//     * 短验码将以短信的方式发送给用户
//     * VOICE：
//     * 短验码将以语音的方式发送给用户
//     * 默认值为 MESSAGE
//     */
//    private String advicesmstype = "MESSAGE";
//
//    /**
//     * 定制短验模板
//     * 参数 issms 为“true”即发送短验时考虑此参数
//     * 格式 json串，例如：
//     * {
//     * "MESSAGE":true, "bbb":123
//     * }
//     */
//    private String smstempldatem;
//
//    /**
//     * 定制短验模板 ID
//     * 参数 issms 为“true”即发送短验时考虑此参数
//     * 用于需要定制短信验证码内容时，请联系您所属的技术支持或销售，此模板标识由易宝生成并告示商户
//     */
//    private String smstemplateid;
//
//    /**
//     * 有效期
//     * 单位：分钟
//     * 若不传则默认有效期24h
//     * 传的值要大于 1min，小于 48h
//     * 传的值若小于 1min 系统置为 1min
//     * 传的值若大于
//     */
//    private String avaliabletime;
//
//    /**
//     * 回调地址
//     */
//    private String callbackurl;
//
//    /**
//     * 请求时间
//     * string(32) Y 格式：yyyy-MM-dd HH:mm:ss
//     */
//    private String requesttime = DateUtil.date().toString("yyyy-MM-dd HH:mm:ss");
//    ;
//
//    /**
//     * 商品名称
//     */
//    private String productname;
//
//    /**
//     * 终端号
//     * 快捷：SQKKSCENEKJ010
//     * 代扣：SQKKSCENE10
//     * 商户需开通对应快捷/代扣权限
//     */
//    private String terminalno = "SQKKSCENEKJ010";
//
//    /**
//     * 分账结果通知地址
//     * 不分账不要传这个参数
//     */
//    private String dividecallbackurl;
//
//    /**
//     * 分账信息
//     * JSON 结构的字符串，
//     * 具体结构可见下文附录之“分账请求参数
//     * dividelist 之 Json 结构示例”
//     * 商户不传就是无分账逻辑的请求
//     * 注：分账信息的错误不影响支付结果
//     * 不分账不要传这个参数
//     */
//    private String newdividejstr;
//
//
//    @Override
//    public void setSign(String prikeyvalue) {
//
//    }
//
//    @Override
//    public PayParam convert(Map<String, Object> map, PayRequest payRequest) {
//
//        this.requestno = payRequest.getPaymentId();
//        if (map.get("bankcard") != null) {
//            this.cardno = map.get("bankcard").toString();
//        }
//        if (map.get("idCard") != null) {
//            this.idcardno = map.get("idCard").toString();
//            this.identityid = map.get("idCard").toString();
//            this.identitytype = "ID_CARD";
//        }
//        if (map.get("userName") != null) {
//            this.username = map.get("userName").toString();
//        }
//        if (map.get("phone") != null) {
//            this.phone = map.get("phone").toString();
//        }
//        this.amount = map.get("amount") != null ? map.get("amount").toString() : "0.01";
//        this.productname = map.get("amount") != null ? map.get("productName").toString() : "iphonex手机壳";
//
//        return this;
//    }
//
//    @Override
//    public PayParam encrypt(String encryptKey) {
//        /*this.userName = Demo.AESEncode(encryptKey, userName);
//        this.bankCardNo = Demo.AESEncode(encryptKey, bankCardNo);
//        this.phone = Demo.AESEncode(encryptKey, phone);
//        this.idCode = Demo.AESEncode(encryptKey, idCode);
//        this.cvv2 = Demo.AESEncode(encryptKey, cvv2);
//        this.validPeriod = Demo.AESEncode(encryptKey, validPeriod);*/
//        return this;
//    }
//}
