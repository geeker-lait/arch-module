package com.unichain.pay.huifu.dto;

import lombok.Data;

/**
 * 汇付支付数据库参数
 *
 * @author dai_dlc@163.com
 * @create 2019.05.07
 */
@Data
public class HuifuPayConfigDto extends BaseConfigDto {
    // 商户客户号
    private String merCustId;
    // 公钥路径
    private String cerPath;
    // 商户私钥路径
    private String pfxPath;
    // 私钥密码
    private String pfxPwd;
    // 测试环境请求地址
    private String requestUrl;
    // 版本号
    private String version;
    // 回调基础ip
    private String notifyUrl;
}
