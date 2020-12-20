package com.unichain.pay.sharelink.domain;

import lombok.Data;

/**
 * 批量代收回盘通知应答实体类
 *
 * @author WPC
 */
@Data
public class CounterofferInfoResult extends BasePayObject {
    private String merchantId;//商户标识
    private String batchNo;//批次号
    private String status;//消息接受状态
    private String reqTime;//请求时间
    private String sign;//签名信息


}
