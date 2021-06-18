//package com.unichain.pay.channel.lianlian.domain;
//
//import com.unichain.pay.core.PayParam;
//import com.unichain.pay.core.PayRequest;
//
//import java.util.Map;
//
//public class BankcardPrePayParam extends EncryptPayParam {
//
//    /**
//     * 用户ID
//     */
//    private String user_id;
//    /**
//     * 商户业务类型	虚拟商品销售：101001 实物商品销售：109001
//     */
//    private String busi_partner;
//    /**
//     * 商户唯一订单号 商户系统唯一订单号
//     */
//    private String no_order;
//
//    /**
//     * 商户订单时间 格式：YYYYMMDDH24MISS 14位数字，精确到秒
//     */
//    private String dt_order;
//    /**
//     * 商品名称
//     */
//    private String name_goods;
//
//    /**
//     * 订单描述
//     */
//    private String info_order;
//
//    /**
//     * 交易金额 该笔订单的资金总额，单位为RMB-元。大于0的数字，精确到小数点后两位。如：49.65
//     */
//    private String money_order;
//    /**
//     * 订单有效时间 分钟为单位，默认为10080分钟（7天），从创建时间开始，过了此订单有效时间此笔订单就会被设置为失败状态不能再重新进行支付。
//     */
//    private String valid_order;
//    /**
//     * 服务器异步通知地址 连连支付支付平台在用户支付成功后通知商户服务端的地址
//     */
//    private String notify_url;
//
//    /**
//     * 风险控制参数
//     */
//    private String risk_item;
//    /**
//     * 分账信息,最多支持三个分账商户,分账参数说明请查看分账参数章节
//     */
//    private String shareing_data;
//    /**
//     * 支付方式 P：新认证支付（借记卡）
//     */
//    private String pay_type;
//    /**
//     * 银行卡号
//     */
//    private String card_no;
//    /**
//     * 银行账号姓名
//     */
//    private String acct_name;
//    /**
//     * 绑定手机号
//     */
//    private String bind_mob;
//    /**
//     * 证件类型 默认为0, 其他类型请查看附录-
//     */
//    private String id_type;
//    /**
//     * 证件号码
//     */
//    private String id_no;
//    /**
//     * 信用卡有效期 信用卡时必填，格式YYMM
//     */
//    private String vali_date;
//    /**
//     * 信用卡CVV2
//     */
//    private String cvv2;
//    /**
//     * 签约协议号
//     */
//    private String no_agree;
//
//    public String getBusi_partner() {
//        return busi_partner;
//    }
//
//    public void setBusi_partner(String busi_partner) {
//        this.busi_partner = busi_partner;
//    }
//
//    public String getNo_order() {
//        return no_order;
//    }
//
//    public void setNo_order(String no_order) {
//        this.no_order = no_order;
//    }
//
//    public String getDt_order() {
//        return dt_order;
//    }
//
//    public void setDt_order(String dt_order) {
//        this.dt_order = dt_order;
//    }
//
//    public String getName_goods() {
//        return name_goods;
//    }
//
//    public void setName_goods(String name_goods) {
//        this.name_goods = name_goods;
//    }
//
//    public String getInfo_order() {
//        return info_order;
//    }
//
//    public void setInfo_order(String info_order) {
//        this.info_order = info_order;
//    }
//
//    public String getMoney_order() {
//        return money_order;
//    }
//
//    public void setMoney_order(String money_order) {
//        this.money_order = money_order;
//    }
//
//    public String getValid_order() {
//        return valid_order;
//    }
//
//    public void setValid_order(String valid_order) {
//        this.valid_order = valid_order;
//    }
//
//    public String getNotify_url() {
//        return notify_url;
//    }
//
//    public void setNotify_url(String notify_url) {
//        this.notify_url = notify_url;
//    }
//
//    public String getRisk_item() {
//        return risk_item;
//    }
//
//    public void setRisk_item(String risk_item) {
//        this.risk_item = risk_item;
//    }
//
//    public String getShareing_data() {
//        return shareing_data;
//    }
//
//    public void setShareing_data(String shareing_data) {
//        this.shareing_data = shareing_data;
//    }
//
//    public String getPay_type() {
//        return pay_type;
//    }
//
//    public void setPay_type(String pay_type) {
//        this.pay_type = pay_type;
//    }
//
//    public String getCard_no() {
//        return card_no;
//    }
//
//    public void setCard_no(String card_no) {
//        this.card_no = card_no;
//    }
//
//    public String getAcct_name() {
//        return acct_name;
//    }
//
//    public void setAcct_name(String acct_name) {
//        this.acct_name = acct_name;
//    }
//
//    public String getBind_mob() {
//        return bind_mob;
//    }
//
//    public void setBind_mob(String bind_mob) {
//        this.bind_mob = bind_mob;
//    }
//
//    public String getId_type() {
//        return id_type;
//    }
//
//    public void setId_type(String id_type) {
//        this.id_type = id_type;
//    }
//
//    public String getId_no() {
//        return id_no;
//    }
//
//    public void setId_no(String id_no) {
//        this.id_no = id_no;
//    }
//
//    public String getVali_date() {
//        return vali_date;
//    }
//
//    public void setVali_date(String vali_date) {
//        this.vali_date = vali_date;
//    }
//
//    public String getCvv2() {
//        return cvv2;
//    }
//
//    public void setCvv2(String cvv2) {
//        this.cvv2 = cvv2;
//    }
//
//    public String getNo_agree() {
//        return no_agree;
//    }
//
//    public void setNo_agree(String no_agree) {
//        this.no_agree = no_agree;
//    }
//
//    public String getUser_id() {
//        return user_id;
//    }
//
//    public void setUser_id(String user_id) {
//        this.user_id = user_id;
//    }
//
//    @Override
//    public PayParam convert(Map<String, Object> map, PayRequest payRequest) {
//        return null;
//    }
//}
