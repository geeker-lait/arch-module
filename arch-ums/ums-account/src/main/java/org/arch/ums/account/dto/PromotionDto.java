package org.arch.ums.account.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Description: 推广信息
 *
 * @author kenzhao
 * @date 2020/4/17 15:00
 */
@Data
public class PromotionDto implements Serializable {
    private static final long serialVersionUID = -8153781878005845392L;
    /**
     * 应用ID
     */
    @NotNull(message = "appId不能为空")
    private String appId;
    /**
     * 手机号
     */
    private String accountName;
    /**
     * 用户来源
     */
    private String source;
    /**
     * app类型: ios android wgt
     */
    private String appType;
    /**
     * 短信码
     */
    private String smsCode;
}