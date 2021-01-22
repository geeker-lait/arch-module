package com.unichain.pay.sharelink.domain;

import lombok.Data;

/**
 * 批量代收回盘通知请求实体类
 *
 * @author WPC
 */
@Data
public class CounterofferInforeq extends BasePayObject {
    private String merchantId;//商户标识
    private String batchNo;//批次号
    private String status;//批量处理状态
    private String reqTime;//请求时间
    private String fileName;//文件名
    private String sign;//签名信息


}
