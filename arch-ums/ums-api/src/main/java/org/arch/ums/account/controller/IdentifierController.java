package org.arch.ums.account.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.beans.Response;
import org.arch.framework.crud.CrudController;
import org.arch.framework.ums.bean.TokenInfo;
import org.arch.framework.utils.SecurityUtils;
import org.arch.ums.account.dto.AuthLoginDto;
import org.arch.ums.account.dto.AuthRegRequest;
import org.arch.ums.account.dto.IdentifierSearchDto;
import org.arch.ums.account.entity.Identifier;
import org.arch.ums.account.service.IdentifierService;
import org.arch.ums.account.service.NameService;
import org.springframework.lang.NonNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.dcenter.ums.security.core.api.tenant.handler.TenantContextHolder;
import top.dcenter.ums.security.jwt.JwtContext;

import javax.validation.constraints.NotNull;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
    private final NameService nameService;
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

    /**
     * 逻辑删除(执行 account_identifier 与 account_name 的逻辑删除):<br>
     * & account_identifier:<br>
     *     1. 更新 deleted 字段值为 1.<br>
     *     2. 对 identifier 字段添加 "_deleted_序号" 后缀;<br>
     *        添加后缀防止用户重新通过此第三方注册时触发唯一索引问题;<br>
     *        添加 序号 以防止多次删除同一个第三方账号时触发唯一索引问题.<br>
     * & account_name:<br>
     *     1. 更新 deleted 字段值为 1.<br>
     * @param id    {@link Identifier#getId()}
     * @return  是否删除成功.
     */
    @NonNull
    @DeleteMapping(value = "/{id:[0-9]+}")
    @Override
    public Response<Boolean> deleteById(@PathVariable(value = "id") Long id) {
        Integer tenantId = Integer.valueOf(tenantContextHolder.getTenantId());
        try {
            // 获取 Identifier
            Identifier identifier = identifierService.findById(id);
            // 逻辑删除
            Response<Boolean> success = Response.success(identifierService.logicDeletedByIdentifier(tenantId, identifier));
            JwtContext.addReAuthFlag(SecurityUtils.getCurrentUserId().toString());
            SecurityContextHolder.clearContext();
            return success;
        }
        catch (Exception e) {
            log.error(String.format("删除用户失败: tenantId: %s, id: %d",
                                    tenantContextHolder.getTenantId(), id), e);
            return Response.success(Boolean.FALSE);
        }
    }

    /**
     * 逻辑删除(执行 account_identifier 与 account_name 的逻辑删除):<br>
     * & account_identifier:<br>
     *     1. 更新 deleted 字段值为 1.<br>
     *     2. 对 identifier 字段添加 "_deleted_序号" 后缀;<br>
     *        添加后缀防止用户重新通过此第三方注册时触发唯一索引问题;<br>
     *        添加 序号 以防止多次删除同一个第三方账号时触发唯一索引问题.<br>
     * & account_name:<br>
     *     1. 更新 deleted 字段值为 1.<br>
     * @param identifier    {@link Identifier#getIdentifier()}
     * @return  是否删除成功.
     */
    @DeleteMapping(value = "/username/{identifier}")
    @NonNull
    public Response<Boolean> logicDeleteByIdentifier(@PathVariable(value = "identifier") String identifier) {
        Integer tenantId = Integer.valueOf(tenantContextHolder.getTenantId());
        try {
            // 获取 Identifier
            Map<String, Object> params = new LinkedHashMap<>(2);
            params.put("tenant_id", tenantId);
            params.put("identifier", identifier);
            Wrapper<Identifier> queryWrapper = Wrappers.<Identifier>query().allEq(params);
            Identifier accountIdentifier = identifierService.findOneBySpec(queryWrapper);

            if (isNull(accountIdentifier)) {
            	return Response.success(Boolean.FALSE, identifier + " 账号不存在");
            }

            // 逻辑删除
            Response<Boolean> success = Response.success(identifierService.logicDeletedByIdentifier(tenantId, accountIdentifier));
            JwtContext.addReAuthFlag(SecurityUtils.getCurrentUserId().toString());
            SecurityContextHolder.clearContext();
            return success;
        }
        catch (Exception e) {
            log.error(String.format("删除用户失败: tenantId: %s, identifier: %s",
                                    tenantContextHolder.getTenantId(), identifier), e);
            return Response.success(Boolean.FALSE);
        }

    }
}