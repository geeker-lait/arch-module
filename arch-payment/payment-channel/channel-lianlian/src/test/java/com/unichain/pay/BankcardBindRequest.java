package com.unichain.pay;

/**
 * https://mpayapi.lianlianpay.com/v1/bankcardbind
 * 银行卡签约申请
 * 请求参数（json）：
 * {
 * "acct_name": "李天增",
 * "bind_mob": "18667341234",
 * "card_no": "8888888888888888",
 * "cvv2": "222",
 * "dt_order": "20180828153543",
 * "id_no": "330401198001014616",
 * "id_type": "0",
 * "no_order": "20180828153543",
 * "notify_url": "http://test.yintong.com.cn/help/notify.php",
 * "oid_partner": "201304121000001004",
 * "pay_type": "P",
 * "risk_item":
 * "{\"user_info_bind_phone\":\"13958069593\",\"user_info_dt_register\":
 * \"20131030122130\",\"frms_ware_category
 * \":\"1009\",\"request_imei”:211,\"request_imsi”:121121,\"request_ip”:
 * 192.168.20.110}",
 * "sign":
 * "tFQhBzzpL9oaBaQAVMPyJ5w4n9hVP9B6cXrLBfq9EsMBPaQS67hcRHAsyUPt89YPzVzE
 * /ILVMx8MJ1s6WSLFz3Oef+aW5cSPBB+edh+PN3L8C3LdMZA9DfU67h1Fa6iq7pVpfoFK4
 * P8Y0rxwBLeKoYoQdpbztW/8oSuFN3k9WHo=",
 * "sign_type": "RSA",
 * "user_id": "20180828153543",
 * "vali_date": "2002"
 * }
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
public class BankcardBindRequest {
    //  平台来源商户号
    private String platform;
    //用户唯一编号
    private String user_id;
    //商户编号是商户在连连支付支付平台上开设结算到银行账户的商户号码，为 18 位数字，如：201304121000001004
    private String oid_partner;
    /**
     * 签名方式 RSA
     */
    private String sign_type;
    /**
     * 签名
     */
    private String sign;
    /**
     * 异步通知地址
     */
    private String notify_url;
    /**
     * 签约订单号，商户签约鉴权唯一订单号
     */
    private String no_order;
    /**
     * 签约请求时间
     * 格式：YYYYMMDDH24MISS 14 位数字，
     * 精确到秒
     */
    private String dt_order;
    /**
     * 支付方式
     * 2：快捷支付（借记卡）
     * 3：快捷支付（信用卡）
     */
    private String pay_type;
    /**
     * 风险控制参数
     * 此字段填写风控参数，采用 json 串的模式传
     * 入，字段名和字段内容彼此对应好，例如：
     * {"frms_ware_category":"2009","user_info_
     * mercht_userno":"12345","user_info_bind_
     * phone":"13812345678","user_info_dt_regi
     * ster":"20141015165530","user_info_full_n
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
     * 银行卡号
     */
    private String card_no;
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
     * 证件号码
     */
    private String id_no;
    /**
     * 信用卡有效期，信用卡时必填，格式 YYMM 如：1412
     */
    private String vali_date;
    /**
     * 信用卡 CVV2，信用卡时必填
     */
    private String cvv2;
}
