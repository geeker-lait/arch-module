package org.arch.ums.account.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.beans.Response;
import org.arch.framework.crud.CrudController;
import org.arch.framework.ums.bean.TokenInfo;
import org.arch.framework.ums.properties.AppProperties;
import org.arch.ums.account.dto.IdentifierSearchDto;
import org.arch.ums.account.entity.Identifier;
import org.arch.ums.account.service.IdentifierService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.dcenter.ums.security.core.api.tenant.handler.TenantContextHolder;

import java.util.List;

import static java.util.Objects.nonNull;

/**
 * 用户-标识(Identifier) 表服务控制器
 *
 * @author YongWu zheng
 * @date 2021-01-29 21:04:11
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/account/identifier")
public class IdentifierController implements CrudController<Identifier, java.lang.Long, IdentifierSearchDto, IdentifierService> {

    private final IdentifierService identifierService;
    private final TenantContextHolder tenantContextHolder;
    private final AppProperties appProperties;

    @Override
    public Identifier resolver(TokenInfo token, Identifier identifier) {
        if (nonNull(token) && nonNull(token.getTenantId())) {
            identifier.setTenantId(token.getTenantId());
        }
        else {
            identifier.setTenantId(appProperties.getSystemTenantId());
        }
        return identifier;
    }

    @Override
    public IdentifierService getCrudService() {
        return identifierService;
    }

    @Override
    public IdentifierSearchDto getSearchDto() {
        return new IdentifierSearchDto();
    }

    /**
     * 查询 identifiers 是否已经存在.
     * @param identifiers    identifiers 列表
     * @return  identifiers 对应的结果集.
     */
    @RequestMapping(value = "/exists", method = {RequestMethod.POST})
    public Response<List<Boolean>> exists(@RequestParam("identifiers") List<String> identifiers, TokenInfo tokenInfo) {
        String tenantId = tenantContextHolder.getTenantId();
        return Response.success(identifierService.exists(identifiers, Integer.valueOf(tenantId)));
    }


//    /**
//     * 根据 identifier 获取用户信息 {@link AuthAccountDto}.
//     * @param identifier    用户唯一标识
//     * @return  返回用户信息 {@link AuthAccountDto}. 不存在返回 null.
//     */
//    @GetMapping("/loadAccount/{identifier}")
//    public AuthAccountDto loadAccountByIdentifier(@PathVariable(value = "identifier") String identifier) {
//        return accountIdentifierService.getAccountByIdentifier(identifier);
//    }

}