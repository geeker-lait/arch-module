package org.arch.framework.ums.filter;

import lombok.extern.slf4j.Slf4j;
import org.arch.framework.beans.exception.AuthenticationException;
import org.arch.framework.beans.exception.BusinessException;
import org.arch.framework.beans.exception.constant.CommonStatusCode;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.arch.framework.beans.Response.failed;
import static top.dcenter.ums.security.common.utils.JsonUtil.responseWithJson;
import static top.dcenter.ums.security.common.utils.JsonUtil.toJsonString;

/**
 * 错误处理器
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.3.15 0:28
 */
@Slf4j
public class ErrorHandlerFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        }
        catch (AuthenticationException | BusinessException e) {
            log.error(e.getMessage(),e);
            int status = HttpStatus.INTERNAL_SERVER_ERROR.value();
            if (e instanceof AuthenticationException)
            {
                status = HttpStatus.UNAUTHORIZED.value();
            }
            responseWithJson(response, status, toJsonString(failed(e.getResponseCode(), e.getMessage())));
        }
        catch (ServletException e) {
            log.error(e.getMessage(),e);
            int status = HttpStatus.INTERNAL_SERVER_ERROR.value();
            responseWithJson(response, status, toJsonString(failed(CommonStatusCode.SERVER_ERROR, e.getMessage())));
        }
    }
}
