package com.unichain.pay;

import lombok.Data;

/**
 * 签约支付申请
 * https://mpayapi.lianlianpay.com/v1/bankcardprepay
 * <p>
 * 加密前报文体内容,报文按照签名原则进行加签，样例如下：
 * {
 * "acct_name": "李天增",
 * "bind_mob": "18667341234",
 * "busi_partner": "101001",
 * "card_no": "8888888888888888",
 * "cvv2": "222",
 * "dt_order": "20180828153543",
 * "id_no": "330401198001014616",
 * "money_order": "55.44",
 * "name_goods": "点读机",
 * "no_order": "20180828153543",
 * "notify_url": "http://test.yintong.com.cn/help/notify.php",
 * "oid_partner": "201304121000001004",
 * "platform": "",
 * "risk_item":
 * "{\"user_info_bind_phone\":\"13958069593\",\"user_info_dt_register\":
 * \"20131030122130\",\"frms_ware_category
 * \":\"1009\",\"request_imei”:211,\"request_imsi”:121121,\"request_ip”:
 * 192.168.20.110}",
 * "shareing_data": "201804110001739005^101001^108.23^大行贷回款分账
 * 2018073000420881350_1",
 * "sign":
 * "HTFW/Tzk4xzGOZGCFD9LwisGg6Wsnn/THQbcLPTz3rqQmdsVXmVcnoyNrPTUh46GD5uq
 * qWbd8KG8Gf26UI0g4S3Oqwb6vDkJb95+jRqNVWeyiDSaxsOqCry1xUs6PR2Qpqm4WPVYa
 * iHOawH3NC3Gav9KcZ25QwIAaq0OMi8K5BQ=",
 * "sign_type": "RSA",
 * "user_id": "20180828153543",
 * "vali_date": "2002",
 * "valid_order": "10080"
 * }
 * <p>
 * <p>
 * 请求参数 json 报文加密传输（pay_load 是以上参数 json 加密结果）放入 http 请求
 * body
 * {
 * "oid_partner": "201304121000001004",
 * "pay_load":
 * "lianpay1_0_1$IjRo9QTNupI7FEKMQq0Cz+11KlnvAyoyrTQi8C03nqLlBGl2dj3Kbd9
 * SoV+nTuNs8iTsvwNVnNt8\r\nLVv3iCZjBJYeo3oLsLb2JxWHpHltUgWBc/YQsVkmRmRc
 * rIDzycxvZ8OIQxwVVk34ad7anOC0RD7C\r\nap6BlcZPTs1RDe6fEaw=$KytJ6nSrXn7O
 * S8EDQLENbgj2lVuiLD/VWD6hp0Cp5vieHK29tRHOCkW6ph+fq3yHU98knbaQiAtU\r\ni
 * pM/tPGWpyuCyWOBP6aayoJDU0RKdJMgqK0MQYQbZPxg7XCQT7Z6YatTiT4ZFCAQd3LfdS
 * m9OX+w\r\nFb0jCS7QsJYiznF86Jk=$QkFOWFlUMUE=$SCjQXBPxHCk6/mQMD1/RnZUfw
 * KukGcgtm+LmJyK6/627+TPCeZ1iG1maCWluSq7C1FsV5DEhknTH\r\n9I5y6U3xyOajSR
 * q5HS42znCo4oDTT8JrLxgBAAy97CdE8Aax9z7iEmDqwhDRQOwUTb7lObmqoD+K\r\nOEC
 * /fAzZGP6dz4iqkbepLB9atXkFYi7+bAX/ZNVgUeBkL7mXHSpQTl1Yrh21qkKBT2OTVu8w
 * eUO2\r\n4xjvV+utC4OgSqdFeWahi5ODS5IWTAuSb+kHcvbvX4UZjDQ8w2s7EwgnlpOzO
 * ggFeyUPawMjjx45\r\nPzT+TbS6/glIjtLISnUuOCrwu0i640vlLIvBCyPbbni+5vNF9k
 * D7uhJEDEXkO9tDo3vF8HHzB4lQ\r\n8gXN5sehoGXvd2Rk9ZhUCVk70YCkXd4lA0Jf9im
 * QLxc/tshoyTI4Cb9pcMLvfbt4vZd5tbARJqLF\r\n92MagCxKbLKPl7v+esiCIgS6/TqK
 * cVzCdcaQKRUonjowrOqn+d+7xumHURtX9yYOAawq066gUCOb\r\no6PHrT8KxrAoQ2STx
 * Eod4c6HjGXseMs9HqzLadJlzgKFCTiXk8O8FrlcaaMehiiZO8+XILCihcI6\r\nzhTgrE
 * 77hstkb5CFfBUwHkdNH24WveERZlluoHUncWwuYof1d28HMjb9dQyFrJR0LIIQYE0V5eO
 * P\r\nhoJXpnPEx8otm+0iqo0cy9Vx257jqb7ezvYMCxkQlbtFuy7Wpko9JsfDcYnwFKEl
 * gm1pSnGD9Ga/\r\nSFPT62tA2ndFRfSy32PS/TcPnZYEvM4TwBVTD2srnNG4NxLmS7fnq
 * U0y06q4XSb0lFQh0XSfFZR4\r\ni6bFsHw6ZWcrlBIgOM1/Lmj5SoKBM9K8R9IcYB/eXZ
 * SVhPRReCk/9g7H0Id7EEFqPmRfuA/XqsH4\r\nowg1KkxN3kQ/XzF1JSeLoGjIyUESLn+
 * C2Sv4+at+7aCX$XmrO6P26GhZqmR9gE75isXSkmq1dgvW40MAIlwVm8e0="
 * }
 */
@Data
public class BankcardRepayRequest {

    /**
     * 平台来源
     * 平台来源能有效区分该交易是从此平台发起，
     * 并能有效定义用户来源
     */
    private String platform;

    /**
     * 用户唯一编号
     * 商户用户唯一编号 保证唯一
     */
    private String user_id;

    /**
     * 交易结算商户编号
     * 商户编号是商户在连连支付支付平台上开设结
     * 算到银行账户的商户号码，为 18 位数字，如：
     * 201304121000001004
     */
    private String oid_partner;

    /**
     * 签名方式
     */
    private String sign_type;

    /**
     * 签名
     */
    private String sign;


    ///////////////////////////订单信息/////////////////////////////


    /**
     * 商户业务类型
     * 虚拟商品销售：101001
     * 实物商品销售：109001
     */
    private String busi_partner;

    /**
     * 商户唯一订单号
     * 商户系统唯一订单号
     */
    private String no_order;

    /**
     * 商户订单时间
     * 格式：YYYYMMDDH24MISS 14 位数字，
     * 精确到秒
     */
    private String dt_order;

    /**
     * 商品名称
     */
    private String name_goods;

    /**
     * 订单描述
     */
    private String info_order;

    /**
     * 交易金额
     * 该笔订单的资金总额，单位为 RMB-元。
     * 大于 0 的数字，精确到小数点后两位。
     * 如：49.65
     */
    private String money_order;

    /**
     * 服务器异步通知地址
     * 连连支付支付平台在用户支付成功后通知商户
     * 服务端的地址，如：
     * http://test.yintong.com.cn/help/notify.php
     */
    private String notify_url;

    /**
     * 订单有效时间
     * 分钟为单位，默认为 10080 分钟（7 天），从
     * 创建时间开始，过了此订单有效时间此笔订单
     * 就会被设置为失败状态不能再重新进行支付
     */
    private String valid_order;

    /**
     * 风险控制参数
     * 此字段填写风控参数，采用 json 串的模式传
     * 入，字段名和字段内容彼此对应好，例如：
     * {"frms_ware_category":"2009","user_info_
     * mercht_userno":"12345","user_info_bind_
     * phone":"13812345678","user_info_dt_regi
     * ster":"20141015165530","user_info_full_n
     * 连连支付 – 银行卡支付 API 商户接口说明书
     * 连连银通电子支付有限公司 |保密 第 17 页 / 共 37 页
     * ame":" 张 三 丰
     * ","user_info_id_no":"33068219900121212
     * 21","user_info_identify_type":"1","user_inf
     * o_identify_state":"1","user_info_id_type":"
     * 0","frms_client_chnl":"10","frms_ip_addr":"
     * 60.191.76.226"}
     * 相关内容见风控参数说明
     * https://open.lianlianpay.com/docs/develo
     * pment/risk-item-overview
     */
    private String risk_item;

    /**
     * 分账信息
     * 分帐商户号 1^分账业务类型 1^分帐金额
     * 1^分账说明 1|分帐商户号 2^分账业务类
     * 型 2^分帐金额 2^分账说明 2|分帐商户号
     * 3^分账业务类型 3^分帐金额 3^分账说明
     * 3
     * 注：
     * 1、 分账商户号：为注册登记在连连支付系
     * 统的商户编号（18 位数字）
     * 2、 分账业务类型:可与主收款业务类型
     * busi_partner 一致
     * 3、 分帐金额：元为单位，保留两位数字
     * 4、 分账说明：不能超过 100 个汉字，不
     * 能有^和|符号
     * 5、分账方不可以是主付款方，分账商户号不
     * 可重复
     * 分账方只支持除主收款方外的 3 个分账方
     */
    private String shareing_data;

    /////////////////////////支付参数 ////////////////////////

    /**
     * 支付方式
     * 2：快捷支付（借记卡）
     * 3：快捷支付（信用卡）
     * D：认证支付（借记卡）
     * P：新认证支付（借记卡）
     */
    private String pay_type;

    /**
     * 银行卡号
     */
    private String card_no;

    /**
     * 银行编号
     */
    private String bank_code;

    /**
     * 银行账号姓名
     */
    private String acct_name;

    /**
     * 绑定手机号
     */
    private String bind_mob;

    /**
     * 证件类型
     * 默认为 0
     * 0：身份证或企业经营证件,1: 户口簿，2：护
     * 照,3.军官证,4.士兵证，5. 港澳居民来往内地通
     * 行证,6. 台湾同胞来往内地通行证,7. 临时身
     * 份证,8. 外国人居留证,9. 警官证, X.其他证
     * 件，10.组织结构代码
     */
    private String id_type;

    /**
     * 信用卡有效期
     * 信用卡时必填，格式 YYMM 如：1412
     */
    private String vali_date;

    /**
     * 信用卡 CVV2
     * 信用卡时必填
     */
    private String cvv2;

    /**
     * 证件号码
     */
    private String id_no;

    /**
     * 签约协议号
     * <p>
     * 二次支付时，支付参数只需送签约协议号
     * 如果不传协议号则
     * card_no,bind_mob,id_type,id_no 必传
     */
    private String no_agree;
}
