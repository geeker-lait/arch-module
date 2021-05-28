package org.arch.auth.sso.session.strategy;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import top.dcenter.ums.security.common.utils.UrlUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static java.util.Optional.ofNullable;

/**
 * 无效 session 时创建新的 session 且重新转发
 *
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.3.1 14:16
 */
@Slf4j
@Component
public class SsoForwardInvalidSessionStrategy implements InvalidSessionStrategy {

    @Override
    public void onInvalidSessionDetected(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String queryString = request.getQueryString();
        if (StringUtils.hasText(queryString)) {
            queryString = "?" + queryString;
        }
        // 设置转发目标 url 为自己, 重新刷新 session

        String forwardUrl = UrlUtil.getUrlPathHelper().getPathWithinApplication(request) + ofNullable(queryString).orElse("");

        if (log.isDebugEnabled()) {
            log.debug("Starting new session and forward");
        }
        request.getSession(true);
        request.getRequestDispatcher(forwardUrl).forward(request, response);

    }
}
