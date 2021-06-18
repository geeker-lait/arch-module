package org.arch.auth.sso.refresh.token.job;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import top.dcenter.ums.security.common.api.tasks.handler.JobHandler;
import top.dcenter.ums.security.core.oauth.properties.Auth2Properties;

/**
 * 第三方登录用户的 accessToken 与 refreshToken 刷新的定时任务.
 *
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.1.11 20:44
 */
@Slf4j
@Component
public class ArchOauth2RefreshTokenJobHandler implements JobHandler {

    private final String cronExp;

    public ArchOauth2RefreshTokenJobHandler(Auth2Properties auth2Properties) {
        this.cronExp = auth2Properties.getRefreshTokenJobCron();
    }

    @Override
    public void run() {
        // TODO 参考 top.dcenter.ums.security.core.oauth.job.RefreshTokenJobHandler

    }

    @Override
    public String cronExp() {
        return this.cronExp;
    }
}
