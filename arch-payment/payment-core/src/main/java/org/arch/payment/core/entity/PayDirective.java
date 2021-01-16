package org.arch.payment.core.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @since 2020-04-07
 */
@Data
@Accessors(chain = true)
@TableName("pay_directive")
public class PayDirective {

    private static final long serialVersionUID = 1L;

    /**
     * 回调地址
     */
    private String callbackUrl;

    /**
     * 通道CODE
     */
    private String channelCode;

    /**
     * 通道ID
     */
    private String channelId;

    /**
     * 指令编码
     */
    private String code;

    /**
     * 指令描述
     */
    private String descr;

    /**
     * 指令名称
     */
    private String name;

    /**
     * 重定向地址
     */
    private String redirectUrl;

    /**
     * 指令的uri
     */
    private String uri;

    /**
     * 指令权重
     */
    private String weight;


}
