package com.unichain.pay.huifu.vo;

import lombok.Data;
import lombok.ToString;

/**
 * @author shaoqing.liu
 * @date 2018/12/8 11:31
 */
@Data
@ToString
public class HuifuBaseResDTO {

    /**
     * 消息类型
     **/
    private String cmd_id;

    /**
     * 商户客户号
     **/
    private String mer_cust_id;


    /**
     * 返回码
     **/
    private String resp_code;

    /**
     * 返回内容
     **/
    private String resp_desc;


    /**
     * 订单号 20位内的字母或数字组合
     **/
    private String order_id;

    /**
     * 订单日期 格式为YYYYMMDD
     **/
    private String order_date;

    /**
     * 用户客户号
     **/
    private String user_cust_id;

    /**
     * 子账户号
     **/
    private String acct_id;

    /**
     * 平台交易号
     **/
    private String platform_seq_id;

}
