package org.arch.framework.ums.tenant.context.handler;

import lombok.extern.slf4j.Slf4j;
import org.arch.framework.ums.properties.AppProperties;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import top.dcenter.ums.security.core.api.tenant.handler.TenantContextHolder;
import top.dcenter.ums.security.core.exception.TenantIdNotFoundException;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.util.StringUtils.hasText;

/**
 * 多租户上下文处理器
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.1.11 21:13
 */
@Slf4j
public class ArchTenantContextHolder implements TenantContextHolder {

    private final ThreadLocal<String> context = new ThreadLocal<>();
    private final String tenantIdHeaderName;
    private final Integer systemTenantId;

    public ArchTenantContextHolder(AppProperties appProperties) {
        this.tenantIdHeaderName = appProperties.getTenantHeaderName();
        this.systemTenantId = appProperties.getSystemTenantId();
        context.set(appProperties.getSystemTenantId().toString());
    }

    @Override
    @NonNull
    public String tenantIdHandle(@NonNull HttpServletRequest request, @Nullable String tenantId) throws TenantIdNotFoundException {

        if (hasText(tenantId)) {
            context.set(tenantId);
            return tenantId;
        }

        // 从请求中获取租户 id
        tenantId = request.getHeader(tenantIdHeaderName);

        if (!hasText(tenantId)) {
            // 请求中没有则设置默认的系统租户 id
            tenantId = systemTenantId.toString();
        }

        context.set(tenantId);
        return tenantId;

    }

    @Override
    @NonNull
    public String getTenantId() throws TenantIdNotFoundException {

        // 已登录用户获取租户 id
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken)) {
            return getTenantId(authentication);
        }
        // 未登录用户获取租户 id
        String tenantId = context.get();
        if (!hasText(tenantId)) {
            return systemTenantId.toString();
        }
        return tenantId;
    }

    /**
     * 清除当前线程的值.
     */
    public void removeContext() {
        context.remove();
    }

}
