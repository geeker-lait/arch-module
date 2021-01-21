package org.arch.auth.sdk.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashSet;
import java.util.Set;

/**
 * csrf 属性
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.1.6 23:15
 */
@ConfigurationProperties(prefix = "arch.csrf")
@Getter
@Setter
public class CsrfProperties {

    /**
     * 是否支持 csrf, 默认: true
     */
    private Boolean enable = Boolean.TRUE;

    /**
     * 需要忽略 csrf 校验的 POST 请求.
     */
    private Set<String> ignoringAntMatcherUrls = new HashSet<>();

}
