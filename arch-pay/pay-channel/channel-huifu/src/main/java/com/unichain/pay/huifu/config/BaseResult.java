package com.unichain.pay.huifu.config;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Auther: zorro
 * @Date: 2019/2/28 21:34
 * @Description:
 */
@Data
public class BaseResult<T> implements Serializable {

    private T data;
    /**
     * 请求订单号
     */
    private String reqTradeNo;
    /**
     * 返回订单号
     */
    private String resTradeNo;
    /**
     * 请求是否成功 200:成功 400:轮循查找 500:失败 600:支付公司订单不存在
     */
    private int status;
    /**
     * 对应第三方的msgCode
     **/
    private String responseCode;
    /**
     * 返回的消息类型
     */
    private String responseTextMessage;
    //手续费 (单位：元)
    private BigDecimal remitProcedureFee;

    /***
     * 有数据标准格式统一处理
     *
     * @return
     */

    public static <T> BaseResult<T> responseSuccessData(T data) {
        BaseResult<T> response = new BaseResult();
        response.setStatus(200);
        response.setData(data);
        return response;
    }

    /***
     * 直接传回错误提示
     *
     * @return
     */
    public static <T> BaseResult<T> responseFaillMsg(String msgCode, String message) {
        BaseResult<T> response = new BaseResult();
        response.setStatus(500);
        response.setResponseCode(msgCode);
        response.setResponseTextMessage(message);
        return response;
    }
}
