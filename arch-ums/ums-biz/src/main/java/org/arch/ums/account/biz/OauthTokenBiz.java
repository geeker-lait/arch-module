package org.arch.ums.account.biz;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.beans.Response;
import org.arch.framework.crud.CrudBiz;
import org.arch.framework.ums.bean.TokenInfo;
import org.arch.framework.utils.SecurityUtils;
import org.arch.ums.account.dto.OauthTokenRequest;
import org.arch.ums.account.dto.OauthTokenSearchDto;
import org.arch.ums.account.entity.OauthToken;
import org.arch.ums.account.rest.OauthTokenRest;
import org.arch.ums.account.service.OauthTokenService;
import org.springframework.beans.BeanUtils;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import top.dcenter.ums.security.core.api.tenant.handler.TenantContextHolder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

/**
 * 第三方账号授权(OauthToken) 表 Biz 服务
 *
 * @author zheng
 * @date 2021-06-06 18:51:03
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class OauthTokenBiz implements CrudBiz<OauthTokenRequest, OauthToken, java.lang.Long, OauthTokenSearchDto, OauthTokenSearchDto, OauthTokenService>, OauthTokenRest {

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
     * 根据 entity 条件查询对象.
     * 注意: 此 API 适合 Feign 远程调用 或 HttpClient 包 json 字符串放入 body 也行.
     *
     * @param request 实体的 request 类型
     * @return DTO
     */
    @Override
    @NonNull
    @Transactional(readOnly = true)
    public OauthTokenSearchDto findOne(OauthTokenRequest request) {
        TokenInfo token = SecurityUtils.getTokenInfo();
        OauthToken oauthToken = resolver(token, request);
        OauthTokenSearchDto searchDto = convertSearchDto(oauthToken);
        OauthToken result = getCrudService().findOneByMapParams(searchDto.searchParams());
        return convertReturnDto(result);
    }

    /**
     * 根据 entity 条件查询对象列表.
     * 注意: 此 API 适合 Feign 远程调用 或 HttpClient 包 json 字符串放入 body 也行.
     *
     * @param request 实体的 request 类型
     * @return DTO List
     */
    @Override
    @NonNull
    @Transactional(readOnly = true)
    public List<OauthTokenSearchDto> find(OauthTokenRequest request) {
        TokenInfo token = SecurityUtils.getTokenInfo();
        OauthToken oauthToken = resolver(token, request);
        OauthTokenSearchDto searchDto = convertSearchDto(oauthToken);
        List<OauthToken> oauthTokenList = getCrudService().findAllByMapParams(searchDto.searchParams());
        return oauthTokenList.stream().map(this::convertReturnDto).collect(Collectors.toList());
    }

    /**
     * 分页查询.
     * 注意: 此 API 适合 Feign 远程调用 或 HttpClient 包 json 字符串放入 body 也行.
     *
     * @param request    实体的 request 类型
     * @param pageNumber 第几页
     * @param pageSize   页大小
     * @return {@link IPage}
     */
    @Override
    @NonNull
    @Transactional(readOnly = true)
    public IPage<OauthTokenSearchDto> page(OauthTokenRequest request, Integer pageNumber, Integer pageSize) {
        TokenInfo token = SecurityUtils.getTokenInfo();
        OauthToken oauthToken = resolver(token, request);
        OauthTokenSearchDto searchDto = convertSearchDto(oauthToken);
        IPage<OauthToken> page = getCrudService().findPage(searchDto.searchParams(), pageNumber, pageSize);
        return page.convert(this::convertReturnDto);
    }

    /**
     * 根据 identifierId 更新 oauthToken
     * @param oauthToken     实体类
     * @return  {@link Response}
     */
    @NonNull
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Boolean updateByIdentifierId(OauthToken oauthToken) {
        LambdaUpdateWrapper<OauthToken> updateWrapper = Wrappers.lambdaUpdate();
        updateWrapper.eq(OauthToken::getAccountIdentifierId, oauthToken.getAccountIdentifierId())
                     .set(nonNull(oauthToken.getAccessToken()), OauthToken::getAccessToken, oauthToken.getAccessToken())
                     .set(nonNull(oauthToken.getExpireIn()), OauthToken::getExpireIn, oauthToken.getExpireIn())
                     .set(nonNull(oauthToken.getRefreshToken()), OauthToken::getRefreshToken,
                          oauthToken.getRefreshToken())
                     .set(nonNull(oauthToken.getRefreshTokenExpireIn()), OauthToken::getRefreshTokenExpireIn,
                          oauthToken.getRefreshTokenExpireIn())
                     .set(nonNull(oauthToken.getAccessCode()), OauthToken::getAccessCode, oauthToken.getAccessCode())
                     .set(nonNull(oauthToken.getScope()), OauthToken::getScope, oauthToken.getScope())
                     .set(nonNull(oauthToken.getTokenType()), OauthToken::getTokenType, oauthToken.getTokenType())
                     .set(nonNull(oauthToken.getIdToken()), OauthToken::getIdToken, oauthToken.getIdToken())
                     .set(nonNull(oauthToken.getMacAlgorithm()), OauthToken::getMacAlgorithm,
                          oauthToken.getMacAlgorithm())
                     .set(nonNull(oauthToken.getMacKey()), OauthToken::getMacKey, oauthToken.getMacKey())
                     .set(nonNull(oauthToken.getCode()), OauthToken::getCode, oauthToken.getCode())
                     .set(nonNull(oauthToken.getOauthToken()), OauthToken::getOauthToken, oauthToken.getOauthToken())
                     .set(nonNull(oauthToken.getOauthTokenSecret()), OauthToken::getOauthTokenSecret,
                          oauthToken.getOauthTokenSecret())
                     .set(nonNull(oauthToken.getOauthCallbackConfirmed()), OauthToken::getOauthCallbackConfirmed,
                          oauthToken.getOauthCallbackConfirmed())
                     .set(nonNull(oauthToken.getUserId()), OauthToken::getUserId, oauthToken.getUserId())
                     .set(nonNull(oauthToken.getScreenName()), OauthToken::getScreenName, oauthToken.getScreenName())
                     .set(nonNull(oauthToken.getExpireTime()), OauthToken::getExpireTime, oauthToken.getExpireTime())
                     .set(OauthToken::getDt, LocalDateTime.now());

        return oauthTokenService.updateBySpec(updateWrapper);
    }

}
