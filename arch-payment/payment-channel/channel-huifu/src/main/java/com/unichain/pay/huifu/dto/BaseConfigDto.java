package com.unichain.pay.huifu.dto;

import lombok.Data;

/**
 * @author: PC
 * @version: v1.0
 * @description: com.unichain.pay.base.model.pojo.dto.channelConfig
 * @date:2019/4/24
 */
@Data
public class BaseConfigDto {
    /**
     * 渠道号
     */
    private String channelCode;
    /***所属公司*/
    private String companyCode;
    /**
     * 业务类别
     */
    private Integer businessType;
    //商户号
    private String merchantNo;
}
