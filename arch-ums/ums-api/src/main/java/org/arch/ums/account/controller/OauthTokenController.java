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

/**
 * 第三方账号授权(OauthToken) 表服务控制器
 *
 * @author YongWu zheng
 * @date 2021-01-29 20:55:28
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/account/oauth/token")
public class OauthTokenController implements CrudController<OauthToken, java.lang.Long, OauthTokenSearchDto, OauthTokenService> {

    private final OauthTokenService oauthTokenService;

    @Override
    public OauthToken resolver(TokenInfo token, OauthToken oauthToken) {
        // TODO 默认实现不处理, 根据 TokenInfo 处理 oauthToken 后返回 oauthToken, 如: tenantId 的处理等.
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