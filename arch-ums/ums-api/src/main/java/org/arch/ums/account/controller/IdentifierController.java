package org.arch.ums.account.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.beans.Response;
import org.arch.framework.beans.exception.AuthenticationException;
import org.arch.framework.crud.CrudController;
import org.arch.framework.ums.bean.TokenInfo;
import org.arch.framework.utils.SecurityUtils;
import org.arch.ums.account.dto.AuthLoginDto;
import org.arch.ums.account.dto.AuthRegRequest;
import org.arch.ums.account.dto.IdentifierSearchDto;
import org.arch.ums.account.entity.Identifier;
import org.arch.ums.account.service.IdentifierService;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.lang.NonNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.dcenter.ums.security.core.api.tenant.handler.TenantContextHolder;
import top.dcenter.ums.security.jwt.JwtContext;

import javax.validation.constraints.NotNull;
import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.arch.framework.beans.exception.constant.ResponseStatusCode.FAILED;

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
        if (isNull(identifier)) {
            identifier =  new Identifier();
        }
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

        try {
            TokenInfo currentUser = SecurityUtils.getCurrentUser();
            if (!currentUser.getIdentifierId().equals(id)) {
                return Response.failed("只能删除已登录的账号");
            }

            Long identifierId = currentUser.getIdentifierId();
            if (!identifierId.equals(id)) {
                log.error("删除用户 id 与 当前用户 id 不匹配: tenantId: {}, id: {}, currentUserId: {}",
                          tenantContextHolder.getTenantId(), id, identifierId);
                return Response.success(Boolean.FALSE);
            }
            // 逻辑删除
            boolean deleteByIdResult = identifierService.deleteById(id);
            Response<Boolean> success = Response.success(deleteByIdResult);
            if (deleteByIdResult) {
                JwtContext.addReAuthFlag(SecurityUtils.getCurrentUserId().toString());
                SecurityContextHolder.clearContext();
            }
            return success;
        }
        catch (AuthenticationException e) {
            return Response.failed("未登录");
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
    public Response<Boolean> logicDeleteByIdentifier(@PathVariable(value = "identifier") String identifier,
                                                     TokenInfo token) {
        if (isNull(token)) {
            return Response.failed("未登录");
        }
        if (!token.getAccountName().equals(identifier)) {
            return Response.failed("只能删除已登录的账号");
        }
        Integer tenantId = Integer.valueOf(tenantContextHolder.getTenantId());
        try {
            // 逻辑删除
            Response<Boolean> success = identifierService.logicDeletedByIdentifier(tenantId, identifier);
            Boolean successData = success.getSuccessData();
            if (nonNull(successData) && successData) {
                JwtContext.addReAuthFlag(SecurityUtils.getCurrentUserId().toString());
                SecurityContextHolder.clearContext();
            }
            return success;
        }
        catch (Exception e) {
            log.error(String.format("删除用户失败: tenantId: %s, identifier: %s",
                                    tenantContextHolder.getTenantId(), identifier), e);
            return Response.success(Boolean.FALSE);
        }

    }

    /**
     * 根据 aid 解绑 identifier(第三方标识) :<br>
     * & account_identifier:<br>
     *     1. 更新 deleted 字段值为 1.<br>
     *     2. 对 identifier 字段添加 "_deleted_序号" 后缀;<br>
     *        添加后缀防止用户重新通过此第三方注册时触发唯一索引问题;<br>
     *        添加 序号 以防止多次删除同一个第三方账号时触发唯一索引问题.<br>
     * @param aid           {@link Identifier#getAid()}
     * @param identifier    {@link Identifier#getIdentifier()}
     * @return  是否解绑成功.
     */
    @DeleteMapping(value = "/unbinding/{aid}/{identifier}")
    @NonNull
    public Response<Boolean> unbinding(@PathVariable(value = "aid") Long aid,
                                @PathVariable(value = "identifier") String identifier, TokenInfo token) {
        if (isNull(token)) {
            return Response.failed("未登录");
        }
        if (!token.getAccountId().equals(aid)) {
            return Response.failed("只能解绑自己的账号");
        }
        try {
            // 解绑
            return Response.success(identifierService.unbinding(identifier, aid));
        }
        catch (Exception e) {
            log.error(String.format("解绑失败: aid: %s, tenantId: %s, identifier: %s",
                                    aid, tenantContextHolder.getTenantId(), identifier), e);
            return Response.success(Boolean.FALSE);
        }
    }

    /**
     * 删除账号
     * @param accountId 账号ID/用户ID/会员ID/商户ID
     * @return  true 表示成功, false 表示失败
     */
    @DeleteMapping(value = "/del/{accountId}")
    @NonNull
    public Response<Boolean> deleteByAccountId(@PathVariable(value = "accountId") Long accountId, TokenInfo token) {

        if (isNull(token)) {
        	return Response.failed("未登录");
        }
        if (!token.getAccountId().equals(accountId)) {
            return Response.failed("只能删除自己的账号");
        }
        try {
            return Response.success(identifierService.deleteByAccountId(accountId, token.getTenantId()));
        }
        catch (Exception e) {
            log.error(String.format("删除账号失败: aid: %s, tenantId: %s",
                                    accountId, token.getTenantId()), e);
            return Response.success(Boolean.FALSE);
        }
    }

    /**
     * 根据 entity 条件查询对象.
     * 注意: 此 API 适合 Feign 远程调用 或 HttpClient 包 json 字符串放入 body 也行.
     * @param entity    实体类
     * @param token     token info
     * @return  {@link Response}
     */
    @Override
    @GetMapping("/single")
    public Response<Identifier> findOne(@RequestBody Identifier entity, TokenInfo token) {
        try {
            resolver(token, entity);
            IdentifierSearchDto searchDto = convertSearchDto(entity);
            Identifier t = getCrudService().findOneByMapParams(searchDto.getSearchParams());
            return Response.success(t);
        } catch (Exception e) {
            if (e instanceof IncorrectResultSizeDataAccessException) {
                return Response.error(FAILED.getCode(),"查询到多个结果");
            } else {
                return Response.error(FAILED.getCode(), e.getMessage());
            }
        }
    }

    /**
     * 根据 entity 条件查询对象列表.
     * 注意: 此 API 适合 Feign 远程调用 或 HttpClient 包 json 字符串放入 body 也行.
     * @param t         实体类
     * @param token     token info
     * @return  {@link Response}
     */
    @Override
    @GetMapping("/find")
    public Response<List<Identifier>> find(@RequestBody Identifier t, TokenInfo token) {
        resolver(token, t);
        IdentifierSearchDto searchDto = convertSearchDto(t);
        return Response.success(getCrudService().findAllByMapParams(searchDto.getSearchParams()));
    }

    /**
     * 分页查询.
     * 注意: 此 API 适合 Feign 远程调用 或 HttpClient 包 json 字符串放入 body 也行.
     * @param entity        实体类
     * @param pageNumber    第几页
     * @param pageSize      页大小
     * @param token         token info
     * @return  {@link Response}
     */
    @Override
    @GetMapping(value = "/page/{pageNumber}/{pageSize}")
    public Response<IPage<Identifier>> page(@RequestBody Identifier entity,
                                                 @PathVariable(value = "pageNumber") Integer pageNumber,
                                                 @PathVariable(value = "pageSize") Integer pageSize,
                                                 TokenInfo token) {
        resolver(token, entity);
        IdentifierSearchDto searchDto = convertSearchDto(entity);
        return Response.success(getCrudService().findPage(searchDto.getSearchParams(), pageNumber, pageSize));
    }
}