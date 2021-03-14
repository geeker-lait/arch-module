package org.arch.framework.ums.jwt.bearer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.util.StringUtils;
import top.dcenter.ums.security.common.consts.SecurityConstants;
import top.dcenter.ums.security.common.enums.ErrorCodeEnum;
import top.dcenter.ums.security.common.utils.IpUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.util.StringUtils.hasText;
import static top.dcenter.ums.security.common.utils.JsonUtil.responseWithJson;
import static top.dcenter.ums.security.common.utils.JsonUtil.toJsonString;
import static top.dcenter.ums.security.common.vo.ResponseResult.fail;

/**
 * 只返回 json 错误信息的 {@link AuthenticationEntryPoint} 实现.
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.3.14 22:38
 */
@Slf4j
public class ArchBearerTokenAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final String bearerTokenHeaderName;
    private final String requestParameterName;

    public ArchBearerTokenAuthenticationEntryPoint(String bearerTokenHeaderName, String requestParameterName) {
        this.bearerTokenHeaderName = bearerTokenHeaderName;
        this.requestParameterName = requestParameterName;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {

        String token = resolveFromAuthorizationHeader(request);
        if (!hasText(token)) {
            token = resolveFromRequestParameters(request);
        }
        String msg = String.format("JWT 认证失败: token=%s, ip=%s, ua=%s, errorMsg=%s",
                                   token,
                                   IpUtil.getRealIp(request),
                                   request.getHeader(SecurityConstants.HEADER_USER_AGENT),
                                   authException.getMessage());
        log.warn(msg, authException);

        // 返回 json
        int status = HttpStatus.UNAUTHORIZED.value();
        responseWithJson(response, status,
                         toJsonString(fail(authException.getMessage(), ErrorCodeEnum.UNAUTHORIZED)));
    }

    private String resolveFromAuthorizationHeader(HttpServletRequest request) {
        String authorization = request.getHeader(this.bearerTokenHeaderName);
        String bearer = "bearer";
        if (!StringUtils.startsWithIgnoreCase(authorization, bearer)) {
            return null;
        }
        return authorization;
    }

    private String resolveFromRequestParameters(HttpServletRequest request) {
        String[] values = request.getParameterValues(this.requestParameterName);
        if (values == null || values.length == 0) {
            return null;
        }
        if (values.length == 1) {
            return values[0];
        }
        return null;
    }
}
