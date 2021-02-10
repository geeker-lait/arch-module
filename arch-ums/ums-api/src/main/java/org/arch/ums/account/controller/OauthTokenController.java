package org.arch.ums.account.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudController;
import org.arch.framework.ums.bean.TokenInfo;
import org.arch.ums.account.dto.OauthTokenSearchDto;
import org.arch.ums.account.entity.OauthToken;
import org.arch.ums.account.service.OauthTokenService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.dcenter.ums.security.core.api.tenant.handler.TenantContextHolder;

import static java.util.Objects.nonNull;

/**
 * 第三方账号授权(OauthToken) 表服务控制器
 *
 * @author YongWu zheng
 * @date 2021-01-30 11:38:48
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/account/oauth/token")
public class OauthTokenController implements CrudController<OauthToken, Long, OauthTokenSearchDto, OauthTokenService> {

    private final TenantContextHolder tenantContextHolder;
    private final OauthTokenService oauthTokenService;

    @Override
    public OauthToken resolver(TokenInfo token, OauthToken oauthToken) {
        // TODO 默认实现不处理, 根据 TokenInfo 处理 oauthToken 后返回 oauthToken, 如: tenantId 的处理等.
        if (nonNull(token) && nonNull(token.getTenantId())) {
            oauthToken.setTenantId(token.getTenantId());
        }
        else {
            oauthToken.setTenantId(Integer.parseInt(tenantContextHolder.getTenantId()));
        }
        return oauthToken;
    }

    @Override
    public OauthTokenService getCrudService() {
        return oauthTokenService;
    }

    @Override
    public OauthTokenSearchDto getSearchDto() {
        return new OauthTokenSearchDto();
    }

}