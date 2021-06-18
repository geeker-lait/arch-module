package org.arch.framework.ums.filter;

import lombok.extern.slf4j.Slf4j;
import org.arch.framework.beans.exception.AuthenticationException;
import org.arch.framework.beans.exception.BusinessException;
import org.arch.framework.beans.exception.constant.CommonStatusCode;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.util.NestedServletException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static java.util.Objects.nonNull;
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
        }catch (NestedServletException e) {
            String msg = e.getMessage();
            log.error(msg, e);
            Throwable cause = e.getCause();

            int status = HttpStatus.INTERNAL_SERVER_ERROR.value();
            CommonStatusCode errorCode = CommonStatusCode.FAILED;
            String errorMsg = msg;
            if (cause instanceof MaxUploadSizeExceededException) {
                MaxUploadSizeExceededException maxUploadSizeExceededException = ((MaxUploadSizeExceededException) cause);
                errorCode = CommonStatusCode.MAX_UPLOAD_SIZE_EXCEEDED_EXCEPTION;
                String message = maxUploadSizeExceededException.getMessage();
                if (nonNull(message)) {
                    errorMsg = message.substring(message.lastIndexOf(":") + 2);
                }
                else {
                    errorMsg = errorCode.getDescr();
                }

            }
            responseWithJson(response, status, toJsonString(failed(errorCode, errorMsg)));
        }
        catch (ServletException e) {
            log.error(e.getMessage(),e);
            int status = HttpStatus.INTERNAL_SERVER_ERROR.value();
            responseWithJson(response, status, toJsonString(failed(CommonStatusCode.SERVER_ERROR, e.getMessage())));
        }
    }
}
