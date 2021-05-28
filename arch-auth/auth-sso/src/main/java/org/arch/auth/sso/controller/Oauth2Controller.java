package org.arch.auth.sso.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.auth.sso.utils.RegisterUtils;
import org.arch.framework.beans.Response;
import org.arch.framework.ums.bean.TokenInfo;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import top.dcenter.ums.security.core.api.oauth.dto.ConnectionDto;
import top.dcenter.ums.security.core.api.oauth.signup.ConnectionService;

import static java.util.Objects.isNull;

/**
 * 第三方账号控制器
 *
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
     *
     * @param identifier 账号标识(OAUTH2)
     * @return 是否成功解绑信息
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
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return Response.failed("解绑失败");
        }
    }

    /**
     * 查询当前账号下的所有绑定的第三方账号
     *
     * @param accountId 账号ID/用户ID/会员ID/商户ID
     * @return 绑定账号集合
     */
    @GetMapping("/connected/{accountId:[0-9]+}")
    public Response<MultiValueMap<String, ConnectionDto>> findAllConnections(@PathVariable("accountId") Long accountId,
                                                                             TokenInfo token) {
        if (isNull(token)) {
            return Response.failed("未登录");
        }
        if (!token.getAccountId().equals(accountId)) {
            return Response.failed("只能查询自己的账号信息");
        }
        try {
            return Response.success(this.connectionService.listAllConnections(accountId.toString()));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return Response.failed("查询第三方绑定谢谢失败");
        }
    }
}
