package org.arch.ums.account.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.beans.Response;
import org.arch.framework.crud.CrudController;
import org.arch.framework.ums.bean.TokenInfo;
import org.arch.ums.account.dto.AuthClientSearchDto;
import org.arch.ums.account.entity.AuthClient;
import org.arch.ums.account.service.AuthClientService;
import org.arch.ums.account.vo.AuthClientVo;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.dcenter.ums.security.core.api.tenant.handler.TenantContextHolder;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.arch.framework.beans.exception.constant.ResponseStatusCode.FAILED;

/**
 * 授权客户端(AuthClient) 表服务控制器
 *
 * @author YongWu zheng
 * @date 2021-01-29 20:48:04
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/account/auth/client")
public class AuthClientController implements CrudController<AuthClient, java.lang.Long, AuthClientSearchDto, AuthClientService> {

    private final AuthClientService authClientService;
    private final TenantContextHolder tenantContextHolder;

    @Override
    public AuthClient resolver(TokenInfo token, AuthClient authClient) {
        if (isNull(authClient)) {
            authClient =  new AuthClient();
        }
        if (nonNull(token) && nonNull(token.getTenantId())) {
            authClient.setTenantId(token.getTenantId());
        }
        else {
            authClient.setTenantId(Integer.parseInt(tenantContextHolder.getTenantId()));
        }
        return authClient;
    }

    @Override
    public AuthClientService getCrudService() {
        return authClientService;
    }

    @Override
    public AuthClientSearchDto getSearchDto() {
        return new AuthClientSearchDto();
    }

    /**
     * 根据 clientId 与 clientSecret 查询 scopes
     * @param clientId      client id
     * @param clientSecret  client secret
     * @return  返回 scopes 集合, 如果不存在, 返回空集合.
     */
    @NonNull
    @PostMapping("/scopes")
    public Response<Set<String>> getScopesByClientIdAndClientSecret(@RequestParam("clientId") String clientId,
                                                                   @RequestParam("clientSecret") String clientSecret) {
        Set<String> scopes;
        try {
            scopes = authClientService.getScopesByClientIdAndClientSecret(clientId, clientSecret);
        }
        catch (Exception e) {
            log.error(String.format("获取 scopes 失败: tenantId: %s, clientId: %s",
                                    tenantContextHolder.getTenantId(), clientId), e);
            return Response.failed("获取 scopes 失败");
        }
        return Response.success(scopes);
    }

    /**
     * 获取所有租户的 scopes
     *
     * @return scopes, Map(tenantId, Map(clientId, AuthClientVo))
     */
    @NonNull
    @GetMapping("/scopes/list")
    public Response<Map<Integer, Map<String, AuthClientVo>>> getAllScopes() {
        Map<Integer, Map<String, AuthClientVo>> allScopes;
        try {
            allScopes = authClientService.getAllScopes();
        }
        catch (Exception e) {
            log.error(String.format("获取所有 scopes 失败: tenantId: %s",
                                    tenantContextHolder.getTenantId()), e);
            return Response.failed("获取所有 scopes 失败");
        }
        return Response.success(allScopes);
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
    public Response<AuthClient> findOne(@RequestBody AuthClient entity, TokenInfo token) {
        try {
            resolver(token, entity);
            AuthClientSearchDto searchDto = convertSearchDto(entity);
            AuthClient t = getCrudService().findOneByMapParams(searchDto.getSearchParams());
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
    public Response<List<AuthClient>> find(@RequestBody AuthClient t, TokenInfo token) {
        resolver(token, t);
        AuthClientSearchDto searchDto = convertSearchDto(t);
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
    public Response<IPage<AuthClient>> page(@RequestBody AuthClient entity,
                                            @PathVariable(value = "pageNumber") Integer pageNumber,
                                            @PathVariable(value = "pageSize") Integer pageSize,
                                            TokenInfo token) {
        resolver(token, entity);
        AuthClientSearchDto searchDto = convertSearchDto(entity);
        return Response.success(getCrudService().findPage(searchDto.getSearchParams(), pageNumber, pageSize));
    }

}