package org.arch.ums.account.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.beans.Response;
import org.arch.framework.crud.CrudController;
import org.arch.framework.ums.bean.TokenInfo;
import org.arch.ums.account.dto.OauthTokenSearchDto;
import org.arch.ums.account.entity.OauthToken;
import org.arch.ums.account.service.OauthTokenService;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.dcenter.ums.security.core.api.tenant.handler.TenantContextHolder;

import javax.validation.Valid;

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

    /**
     * 根据 identifierId 更新 oauthToken
     * @param oauthToken     实体类
     * @return  {@link Response}
     */
    @NonNull
    @PostMapping(value = "/update/by/identifier/id")
    public Response<Boolean> updateByIdentifierId(@RequestBody @Valid OauthToken oauthToken) {
        try {
            return Response.success(oauthTokenService.updateByIdentifierId(oauthToken));
        }
        catch (Exception e) {
            log.error(String.format("更新 oauthToken 失败: identifierId: %s",oauthToken.getAccountIdentifierId()), e);
            return Response.success(Boolean.FALSE, "更新 oauthToken 失败");
        }
    }

}