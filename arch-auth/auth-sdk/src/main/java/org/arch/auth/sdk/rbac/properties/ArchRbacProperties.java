package org.arch.auth.sdk.rbac.properties;

import lombok.Getter;
import lombok.Setter;
import org.arch.auth.sdk.factory.MixPropertySourceFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.access.PermissionEvaluator;
import top.dcenter.ums.security.core.permission.evaluator.UriAuthoritiesPermissionEvaluator;

import java.util.ArrayList;
import java.util.List;

/**
 * RBAC 属性
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.1.17 21:11
 */
@Getter
@Setter
@Configuration
@PropertySource(value = {"classpath:auth-rbac.yml"}, factory = MixPropertySourceFactory.class)
@ConfigurationProperties(prefix = "ums.rbac")
public class ArchRbacProperties {

    /**
     * 用户角色层级配置，默认为 空.<br>
     * 分隔符为:" > ". 例如: ROLE_ADMIN 拥有 ROLE_USER 权限则表示为: {@code ROLE_ADMIN > ROLE_USER > ROLE_EMPLOYEE}<br>
     * 注意:
     * <pre>
     * // ROLE_ADMIN 拥有 ROLE_USER 与 ROLE_EMPLOYEE 权限, ROLE_USER 拥有 ROLE_EMPLOYEE 权限.
     * ROLE_ADMIN > ROLE_USER > ROLE_EMPLOYEE
     * // 等价于
     * ROLE_ADMIN > ROLE_USER
     * ROLE_USER > ROLE_EMPLOYEE
     * </pre>
     */
    private List<String> roleHierarchy = new ArrayList<>();

    /**
     * 403 页面, 默认 空
     */
    private String accessDenyPage;

    /**
     * 权限表达式, 当 {@code enableRestfulApi=false} 或者有 @EnableGlobalMethodSecurity 注释时生效, 默认为 isAuthenticated(). <br>
     * <pre>
     * String accessExp = "isAuthenticated()";
     * // 配置等效与
     * httpSecurity.authorizeRequests().anyRequest().access(isAuthenticated());
     * </pre>
     */
    private String accessExp = "isAuthenticated()";

    /**
     * 权限表达式, 当 {@code enableRestfulApi=true} 且没有 @EnableGlobalMethodSecurity 注释时生效, 默认为 hasPermission(request, authentication).
     * hasPermission 表达式默认实现为 {@link UriAuthoritiesPermissionEvaluator}, 想自定义逻辑, 实现 {@link PermissionEvaluator} 即可替换.<br>
     * <pre>
     * String accessExp = "hasPermission(request, authentication)";
     * // 配置等效与
     * httpSecurity.authorizeRequests().anyRequest().access(hasPermission(request, authentication));
     * </pre>
     */
    private String restfulAccessExp = "hasPermission(request, authentication)";

    /**
     * 是否支持 restful Api (前后端交互接口的风格; 如: 查询(GET),添加(POST),修改(PUT),删除(DELETE)), 默认: true.<br>
     * 当 {@code enableRestfulApi=false} 时 {@code accessExp} 权限表达式生效,
     * 当 {@code enableRestfulApi=true} 时 {@code restfulAccessExp} 权限表达式生效.
     */
    private Boolean enableRestfulApi = true;
}
