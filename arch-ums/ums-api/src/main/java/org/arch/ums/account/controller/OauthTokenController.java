package org.arch.ums.account.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.beans.Response;
import org.arch.framework.crud.CrudController;
import org.arch.framework.ums.bean.TokenInfo;
import org.arch.ums.account.dto.OauthTokenRequest;
import org.arch.ums.account.dto.OauthTokenSearchDto;
import org.arch.ums.account.entity.OauthToken;
import org.arch.ums.account.service.OauthTokenService;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.dcenter.ums.security.core.api.tenant.handler.TenantContextHolder;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;
import static org.arch.framework.beans.exception.constant.ResponseStatusCode.FAILED;

/**
 * 第三方账号授权(OauthToken) 表服务控制器
 *
 * @author YongWu zheng
 * @date 2021-05-15 22:05:25
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/account/oauth/token")
public class OauthTokenController implements CrudController<OauthTokenRequest, OauthToken, java.lang.Long, OauthTokenSearchDto, OauthTokenService> {

    private final TenantContextHolder tenantContextHolder;
    private final OauthTokenService oauthTokenService;

    @Override
    public OauthToken resolver(TokenInfo token, OauthTokenRequest request) {
        OauthToken oauthToken = new OauthToken();
        if (nonNull(request)) {
            BeanUtils.copyProperties(request, oauthToken);
        }
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
    @PostMapping(value = "/updateByIdentifierId")
    public Response<Boolean> updateByIdentifierId(@RequestBody @Valid OauthToken oauthToken) {
        try {
            return Response.success(oauthTokenService.updateByIdentifierId(oauthToken));
        }
        catch (Exception e) {
            log.error(String.format("更新 oauthToken 失败: identifierId: %s",oauthToken.getAccountIdentifierId()), e);
            return Response.error(FAILED.getCode(), "更新 oauthToken 失败");
        }
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
    public Response<OauthTokenSearchDto> findOne(@RequestBody @Valid OauthTokenRequest request, TokenInfo token) {
        try {
            OauthToken oauthToken = resolver(token, request);
            OauthTokenSearchDto searchDto = convertSearchDto(oauthToken);
            OauthToken result = getCrudService().findOneByMapParams(searchDto.searchParams());
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
    public Response<List<OauthTokenSearchDto>> find(@RequestBody @Valid OauthTokenRequest request, TokenInfo token) {
        OauthToken oauthToken = resolver(token, request);
        OauthTokenSearchDto searchDto = convertSearchDto(oauthToken);
        try {
            List<OauthToken> oauthTokenList = getCrudService().findAllByMapParams(searchDto.searchParams());
            return Response.success(oauthTokenList.stream().map(this::convertSearchDto).collect(Collectors.toList()));
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
    public Response<IPage<OauthTokenSearchDto>> page(@RequestBody @Valid OauthTokenRequest request,
                                                     @PathVariable(value = "pageNumber") Integer pageNumber,
                                                     @PathVariable(value = "pageSize") Integer pageSize,
                                                     TokenInfo token) {
        OauthToken oauthToken = resolver(token, request);
        OauthTokenSearchDto searchDto = convertSearchDto(oauthToken);
        try {
            IPage<OauthToken> page = getCrudService().findPage(searchDto.searchParams(), pageNumber, pageSize);
            return Response.success(page.convert(this::convertSearchDto));
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return Response.error(FAILED.getCode(), e.getMessage());
        }
    }

}
