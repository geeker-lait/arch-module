package org.arch.ums.account.service;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.beans.Response;
import org.arch.framework.crud.CrudService;
import org.arch.ums.account.dao.OauthTokenDao;
import org.arch.ums.account.entity.OauthToken;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static java.util.Objects.nonNull;

/**
 * 第三方账号授权(OauthToken) 表服务层
 *
 * @author YongWu zheng
 * @date 2021-02-26 13:25:18
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class OauthTokenService extends CrudService<OauthToken, java.lang.Long> {
    private final OauthTokenDao oauthTokenDao = (OauthTokenDao) crudDao;

    /**
     * 根据 identifierId 更新 oauthToken
     * @param oauthToken     实体类
     * @return  {@link Response}
     */
    @NonNull
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public Boolean updateByIdentifierId(@NonNull OauthToken oauthToken) {

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
                     .set(OauthToken::getSt, LocalDateTime.now());

        return oauthTokenDao.update(updateWrapper);
    }
}
