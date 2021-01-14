package org.arch.framework.ums.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * app 属性
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.1.6 23:15
 */
@ConfigurationProperties(prefix = "arch.app")
@Getter
@Setter
public class AppProperties {

    /**
     * appId 的值
     */
    private String appId;

    /**
     * appCode 的值
     */
    private String appCode;

}
