package org.arch.auth.sso.properties;

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
     * appId 的请求头名称
     */
    private String appIdHeaderName = "appId";

    /**
     * appCode 的请求头名称
     */
    private String appCodeHeaderName = "appCode";

}
