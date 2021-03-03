package org.arch.auth.sso.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.auth.sso.utils.RegisterUtils;
import org.arch.framework.beans.Response;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.dcenter.ums.security.core.api.oauth.signup.ConnectionService;

import static java.util.Objects.isNull;

/**
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.3.3 14:26
 */
@RestController
@RequestMapping("/auth2")
@RequiredArgsConstructor
@Slf4j
public class Oauth2Controller {

    private final ConnectionService connectionService;

    /**
     * 解绑第三方账号 identifier
     * @param identifier    账号标识(OAUTH2)
     * @return  是否成功解绑信息
     */
    @DeleteMapping("/unbinding/{identifier}")
    public Response<Boolean> unbinding(@PathVariable("identifier") String identifier) {
        try {
            String[] providers = RegisterUtils.getProvideIdAndProviderUserIdByIdentifierForOauth2(identifier);
            if (isNull(providers)) {
                return Response.failed(identifier + " 不是第三方账号");
            }
            this.connectionService.unbinding(identifier, providers[0], providers[1]);
            return Response.success(Boolean.TRUE);
        }
        catch (Exception e) {
            log.error(e.getMessage(),e);
            return Response.failed("解绑失败");
        }
    }
}
