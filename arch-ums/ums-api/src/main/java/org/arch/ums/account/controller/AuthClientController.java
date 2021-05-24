package org.arch.ums.account.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.beans.Response;
import org.arch.framework.crud.CrudController;
import org.arch.framework.ums.bean.TokenInfo;
import org.arch.ums.account.dto.AuthClientRequest;
import org.arch.ums.account.dto.AuthClientSearchDto;
import org.arch.ums.account.entity.AuthClient;
import org.arch.ums.account.service.AuthClientService;
import org.arch.ums.account.vo.AuthClientVo;
import org.springframework.beans.BeanUtils;
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

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.arch.framework.beans.exception.constant.ResponseStatusCode.FAILED;

/**
 * 授权客户端(AuthClient) 表服务控制器
 *
 * @author YongWu zheng
 * @date 2021-05-15 21:37:00
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/account/auth/client")
public class AuthClientController implements CrudController<AuthClientRequest, AuthClient, java.lang.Long, AuthClientSearchDto, AuthClientService> {

    private final TenantContextHolder tenantContextHolder;
    private final AuthClientService authClientService;

    @Override
    public AuthClient resolver(TokenInfo token, AuthClientRequest request) {
        AuthClient authClient = new AuthClient();
        if (nonNull(request)) {
            BeanUtils.copyProperties(request, authClient);
        }
        if (nonNull(token) && nonNull(token.getTenantId())) {
            authClient.setTenantId(token.getTenantId());
        }
        else {
            authClient.setTenantId(Integer.parseInt(tenantContextHolder.getTenantId()));
        }
        if (isNull(authClient.getScopes())) {
            authClient.setScopes("");
        }
        if (isNull(authClient.getRoleIds())) {
            authClient.setScopes("");
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
     *
     * @param request 实体的 request 类型
     * @param token   token info
     * @return {@link Response}
     */
    @Override
    @NonNull
    @GetMapping("/single")
    public Response<AuthClientSearchDto> findOne(@RequestBody @Valid AuthClientRequest request, TokenInfo token) {
        try {
            AuthClient authClient = resolver(token, request);
            AuthClientSearchDto searchDto = convertSearchDto(authClient);
            AuthClient result = getCrudService().findOneByMapParams(searchDto.searchParams());
            return Response.success(convertSearchDto(result));
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            if (e instanceof IncorrectResultSizeDataAccessException) {
                return Response.error(FAILED.getCode(), "查询到多个结果");
            }
            else {
                return Response.error(FAILED.getCode(), e.getMessage());
            }
        }
    }

    /**
     * 根据 entity 条件查询对象列表.
     * 注意: 此 API 适合 Feign 远程调用 或 HttpClient 包 json 字符串放入 body 也行.
     *
     * @param request 实体的 request 类型
     * @param token   token info
     * @return {@link Response}
     */
    @Override
    @NonNull
    @GetMapping("/find")
    public Response<List<AuthClientSearchDto>> find(@RequestBody @Valid AuthClientRequest request, TokenInfo token) {
        AuthClient authClient = resolver(token, request);
        AuthClientSearchDto searchDto = convertSearchDto(authClient);
        try {
            List<AuthClient> authClientList = getCrudService().findAllByMapParams(searchDto.searchParams());
            return Response.success(authClientList.stream().map(this::convertSearchDto).collect(Collectors.toList()));
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return Response.error(FAILED.getCode(), e.getMessage());
        }
    }

    /**
     * 分页查询.
     * 注意: 此 API 适合 Feign 远程调用 或 HttpClient 包 json 字符串放入 body 也行.
     *
     * @param request    实体的 request 类型
     * @param pageNumber 第几页
     * @param pageSize   页大小
     * @param token      token info
     * @return {@link Response}
     */
    @Override
    @NonNull
    @GetMapping(value = "/page/{pageNumber}/{pageSize}")
    public Response<IPage<AuthClientSearchDto>> page(@RequestBody @Valid AuthClientRequest request,
                                                     @PathVariable(value = "pageNumber") Integer pageNumber,
                                                     @PathVariable(value = "pageSize") Integer pageSize,
                                                     TokenInfo token) {
        AuthClient authClient = resolver(token, request);
        AuthClientSearchDto searchDto = convertSearchDto(authClient);
        try {
            IPage<AuthClient> page = getCrudService().findPage(searchDto.searchParams(), pageNumber, pageSize);
            return Response.success(page.convert(this::convertSearchDto));
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return Response.error(FAILED.getCode(), e.getMessage());
        }
    }

}
