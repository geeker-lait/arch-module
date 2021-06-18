//package com.unichain.pay.yeepay.domain;
//
//
//import cn.hutool.core.date.DateUnit;
//import cn.hutool.core.date.DateUtil;
//import com.unichain.pay.core.PayParam;
//import com.unichain.pay.core.PayRequest;
//import lombok.Data;
//
//import java.util.Date;
//import java.util.Map;
//
///**
// * 绑卡申请
// *
// * @date 2018-8-31 03:30:56
// */
//@Data
//public class BankcardBindParam implements PayParam {
//
//    //商户编号 merchantno string(32) Y 易宝平台为商户分配的唯一标识
//    private String merchantno;
//    //业务请求号 requestno string(64) Y 商户生成的唯一绑卡请求号
//    private String requestno;
//    //用户标识 identityid; string(32) Y 商户生成的用户唯一标识
//    private String identityid;
//    //用户标识类型 identitytype string(32) Y MAC：网卡地址 IMEI：国际移动设备标识 ID_CARD：用户身份证号 PHONE：手机号 EMAIL：邮箱 USER_ID：用户 id  AGREEMENT_NO：用户纸质订单协议号
//    private String identitytype;
//    //卡号 cardno string(32) Y
//    private String cardno;
//    //证件号 idcardno string(64) Y
//    private String idcardno;
//    //证件类型 idcardtype string(16) Y 固定值 ID：身份证
//    private String idcardtype;
//    //用户姓名 username string(21) Y 只支持中文
//    private String username;
//    //手机号 phone string(32) Y 11 位数字
//    private String phone;
//    //是否发短信 issms boolean Y true:有短验
//    private boolean issms;
//
//    //建议短验发送类型 advicesmstype string(32) N MESSAGE: 短验码将以短信的方式发送给用户 VOICE: 短验码将以语音的方式发送给用户默认值为MESSAGE 注意：当 issms=true 时，传递该字段才有意义。
//    private String advicesmstype;
//    //定制短验模板 ID smstemplateid string(64) N 用于需要定制短信验证码内容时，请联系您所属的技术支持或销售，此模板标识由易宝生成并告示商户 注意：当 issms=true 时，传递该字段 才有意义。
//    private String smstemplateid;
//    //短验模板 smstempldatemsg string(80) N 注意：当 issms=true 时，传递该字段才有意义。
//    private String smstempldatemsg;
//    //绑卡订单有效期 avaliabletime string(16) N 单位：分钟 若不传则默认有效期 24h 传的值要大于 1min，小于 48h 传的值若小于 1min 系统置为 1min 传的值若大于 48h 系统置为 48h
//    private String avaliabletime;
//    //回调地址 callbackurl string(300) N 用于绑卡超时时服务器点对点回调通知商户
//    private String callbackurl;
//    //请求时间 requesttime string(32) Y 格式：yyyy-MM-dd HH:mm:ss
//    private String requesttime;
//    //鉴权类型 authtype string(32) Y COMMON_FOUR(验四)，允许商户在请求中指定是走验三还是 走验四（注：商户在易宝需开通拥有鉴权验四的鉴权类型）
//    private String authtype;
//    //备注 remark string(256) N
//    private String remark;
//    //扩展信息 extinfos string N 扩展信息
//    private String extinfos;
//
//    @Override
//    public void setSign(String prikeyvalue) {
//
//    }
//
//    @Override
//    public PayParam convert(Map<String, Object> map, PayRequest payRequest) {
//        this.requestno = payRequest.getPaymentId();
//        this.identityid = map.get("idCard").toString();
//        this.identitytype = "ID_CARD";
//        this.cardno = map.get("bankcard").toString();
//        this.idcardno = map.get("idCard").toString();
//        this.idcardtype = "ID";
//        this.username = map.get("userName").toString();
//        this.phone = map.get("phone").toString();
//        this.issms = true;
//        this.requesttime = DateUtil.format(new Date(),"yyyy-MM-dd HH:mm:ss");
//        this.authtype = "COMMON_FOUR";
//        return this;
//    }
//
//    @Override
//    public PayParam encrypt(String encryptKey) {
//
//        return this;
//    }
//
//
//}
