package org.arch.framework.ums.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * client 属性 与 多租户属性
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.1.6 23:15
 */
@ConfigurationProperties(prefix = "arch.app")
@Getter
@Setter
public class AppProperties {

    /**
     * clientId 的值
     */
    private String clientId;

    /**
     * clientSecret 的值
     */
    private String clientSecret;

    /**
     * 系统租户 id, 默认: 0
     */
    private final Integer systemTenantId = 0;

    /**
     * 行级多租户字段名称, 默认: tenant_id
     */
    private final String tenantIdColumn = "tenant_id";

    /**
     * tenantId 的请求头名称; 租户登录时通过请求头传递租户 ID, 本系统用户不需要传递租户 ID, 已设置默认租户 ID.
     */
    private final String tenantHeaderName = "tenantId";

}
