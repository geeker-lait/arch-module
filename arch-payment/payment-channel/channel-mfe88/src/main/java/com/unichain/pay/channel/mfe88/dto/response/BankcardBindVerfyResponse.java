package com.unichain.pay.channel.mfe88.dto.response;

import com.unichain.pay.channel.mfe88.dto.BasePayResponse;

/**
 * 签约验证
 */
public class BankcardBindVerfyResponse extends BasePayResponse {

    /**
     * 商户唯一订单号 商户系统唯一订单号
     */
    private String no_order;

    /**
     * 签约协议号
     */
    private String no_agree;

    /**
     * 返回为前六后四中间用*号替换的卡号
     */
    private String card_no;
    /**
     * 商户用户ID
     */
    private String user_id;

    public String getNo_order() {
        return no_order;
    }

    public void setNo_order(String no_order) {
        this.no_order = no_order;
    }

    public String getNo_agree() {
        return no_agree;
    }

    public void setNo_agree(String no_agree) {
        this.no_agree = no_agree;
    }

    public String getCard_no() {
        return card_no;
    }

    public void setCard_no(String card_no) {
        this.card_no = card_no;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
