package org.arch.ums.account.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.beans.Response;
import org.arch.framework.crud.CrudController;
import org.arch.framework.ums.bean.TokenInfo;
import org.arch.ums.account.dto.AuthLoginDto;
import org.arch.ums.account.dto.AuthRegRequest;
import org.arch.ums.account.dto.IdentifierSearchDto;
import org.arch.ums.account.entity.Identifier;
import org.arch.ums.account.service.IdentifierService;
import org.springframework.lang.NonNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.dcenter.ums.security.core.api.tenant.handler.TenantContextHolder;

import javax.validation.constraints.NotNull;
import java.util.List;

import static java.util.Objects.isNull;
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

    @Override
    public Identifier resolver(TokenInfo token, Identifier identifier) {
        if (nonNull(token) && nonNull(token.getTenantId())) {
            identifier.setTenantId(token.getTenantId());
        }
        else {
            identifier.setTenantId(Integer.parseInt(tenantContextHolder.getTenantId()));
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
    @PostMapping(value = "/exists")
    @NonNull
    public Response<List<Boolean>> exists(@RequestParam(value = "identifiers") List<String> identifiers) {
        String tenantId = tenantContextHolder.getTenantId();
        List<Boolean> exists;
        try {
            exists = identifierService.exists(identifiers, Integer.valueOf(tenantId));
        }
        catch (Exception e) {
            log.error(String.format("查询 %s 是否已经存在: tenantId: %s",
                                    identifiers, tenantContextHolder.getTenantId()), e);
            return Response.failed(String.format("查询 %s 是否已经存在失败", identifiers));
        }

        return Response.success(exists);
    }


    /**
     * 根据 identifier 获取用户信息 {@link AuthLoginDto}.
     * @param identifier    用户唯一标识
     * @return  返回用户信息 {@link AuthLoginDto}. 不存在返回 null.
     */
    @GetMapping("/load/{identifier}")
    @NonNull
    public Response<AuthLoginDto> loadAccountByIdentifier(@PathVariable(value = "identifier") String identifier) {

        AuthLoginDto authLoginDto;
        try {
            authLoginDto = identifierService.loadAccountByIdentifier(identifier,
                                                                     Integer.valueOf(tenantContextHolder.getTenantId()));
        }
        catch (Exception e) {
            log.error(String.format("获取用户信息失败: tenantId: %s, identifier: %s",
                                    tenantContextHolder.getTenantId(), identifier), e);
            return Response.failed("获取用户信息失败");
        }

        if (isNull(authLoginDto)) {
            return Response.failed("用户注册失败");
        }

        return Response.success(authLoginDto);
    }

    /**
     * 注册用户
     * @param authRegRequest    注册用户参数封装
     * @return  返回 {@link AuthLoginDto}, 注册失败 {@link Response#getData()} 返回 null.
     */
    @PostMapping(value = "/register")
    @NotNull
    public Response<AuthLoginDto> register(@Validated AuthRegRequest authRegRequest) {
        AuthLoginDto register;
        try {
            register = identifierService.register(authRegRequest);
        }
        catch (Exception e) {
            log.error(String.format("用户注册失败: tenantId: %s, identifier: %s",
                                    authRegRequest.getTenantId(), authRegRequest.getIdentifier()),
                      e);
            return Response.failed("用户注册失败");
        }

        if (isNull(register)) {
            return Response.failed("用户注册失败");
        }

        return Response.success(register);
    }
}