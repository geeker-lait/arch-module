package com.unichain.pay.huifu.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author: PC
 * @version: v1.0
 * @description: com.unichain.pay.base.payapi.huifupay.properties
 * @date:2019/3/23
 */

@Component
@Data
public class HuifuProperties {


    /**
     * 商户号 hfMerId NP2590
     **/
    @Value("${pay.withhold.huifu.merchantId}")
    private String merchantNo;

    /**
     * 版本号
     **/
    @Value("${pay.withhold.huifu.version}")
    private String version;

    /**
     * 商户客户号 6666000000061264
     **/
    @Value("${pay.withhold.huifu.merCustId}")
    private String merCustId;

    /**
     * 商户账户号 76533
     **/
    private String acct_id;

    /**
     * 汇付天下商编号  固定为 100001
     **/
    private String npayMerId;


    /**
     * 商户私钥路径
     **/
    @Value("${pay.withhold.huifu.pfxPath}")
    private String pathPfx;

    /**
     * 私钥密码
     **/
    @Value("${pay.withhold.huifu.pfxPwd}")
    private String pathPassWord;

    /**
     * 汇付公钥
     **/
    @Value("${pay.withhold.huifu.cerPath}")
    private String pathCer;

    /**
     * 请求url
     **/
    @Value("${pay.withhold.huifu.requestUrl}")
    private String requestUrl;
    /*********************委贷***********************/
    /**
     * 委托人用户名
     */
    @Value("${pay.withhold.huifu.loan.clientName}")
    private String clientName;
    /**
     * 委托人app_token
     */
    @Value("${pay.withhold.huifu.loan.appToken}")
    private String appToken;
    /**
     * 委托人app_key
     */
    @Value("${pay.withhold.huifu.loan.appKey}")
    private String appKey;
    /**
     * 放款通用请求url
     */
    @Value("${pay.withhold.huifu.loan.requestBaseUrl}")
    private String loanReqBaseUrl;
    @Value("${pay.record.ip}")
    private String baseNotify;
    @Value("${pay.withhold.huifu.loan.companyName}")
    private String companyName;

}
