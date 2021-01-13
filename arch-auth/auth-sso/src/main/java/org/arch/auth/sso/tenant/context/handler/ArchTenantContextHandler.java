package org.arch.auth.sso.tenant.context.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import top.dcenter.ums.security.common.enums.ErrorCodeEnum;
import top.dcenter.ums.security.core.api.tenant.handler.TenantContextHolder;
import top.dcenter.ums.security.core.exception.TenantIdNotFoundException;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.util.StringUtils.hasText;
import static top.dcenter.ums.security.core.mdc.utils.MdcUtil.getMdcTraceId;

/**
 * 多租户上下文处理器
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.1.11 21:13
 */
//@Component
@Slf4j
public class ArchTenantContextHandler implements TenantContextHolder {

    private final ThreadLocal<String> context = new ThreadLocal<>();

    @Override
    @NonNull
    public String tenantIdHandle(@NonNull HttpServletRequest request, @Nullable String tenantId) throws TenantIdNotFoundException {
        // TODO 从 request 中获取 tenant Id

        if (!hasText(tenantId)) {
            throw new TenantIdNotFoundException(ErrorCodeEnum.TENANT_ID_NOT_FOUND, getMdcTraceId(), request.getRequestURI());
        }
        context.set(tenantId);
        return tenantId;
    }

    @Override
    @NonNull
    public String getTenantId() throws TenantIdNotFoundException {

        // 未登录用户获取租户 id
        String tenantId = context.get();
        if (!hasText(tenantId)) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken)) {
                // 已登录用户获取租户 id
                return getTenantId(authentication);
            }
            throw new TenantIdNotFoundException(ErrorCodeEnum.TENANT_ID_NOT_FOUND, getMdcTraceId(), getMdcTraceId());
        }

        return tenantId;
    }

    /**
     * 清除线程池的局部变量.
     */
    public void removeContext() {
        context.remove();
    }

}
