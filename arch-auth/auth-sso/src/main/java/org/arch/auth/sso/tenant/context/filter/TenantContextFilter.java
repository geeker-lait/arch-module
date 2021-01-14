package org.arch.auth.sso.tenant.context.filter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.auth.sso.tenant.context.handler.ArchTenantContextHandler;
import org.springframework.lang.NonNull;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 处理租户 id 过滤器
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.1.11 21:48
 */
@RequiredArgsConstructor
@Slf4j
public class TenantContextFilter extends OncePerRequestFilter {

    private final ArchTenantContextHandler archTenantContextHolder;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        try {
            archTenantContextHolder.tenantIdHandle(request, null);
        }
        catch (Exception e) {
            log.error("解析多租户 id 错误", e);
        }
        filterChain.doFilter(request, response);

        archTenantContextHolder.removeContext();
    }
}
