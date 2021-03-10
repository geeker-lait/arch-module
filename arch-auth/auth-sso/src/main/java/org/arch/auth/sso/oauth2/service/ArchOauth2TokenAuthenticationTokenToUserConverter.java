package org.arch.auth.sso.oauth2.service;

import org.arch.framework.ums.bean.TokenInfo;
import org.arch.framework.ums.userdetails.ArchUser;
import org.arch.framework.utils.SecurityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.server.resource.authentication.AbstractOAuth2TokenAuthenticationToken;
import org.springframework.stereotype.Component;
import top.dcenter.ums.security.common.api.userdetails.converter.AuthenticationToUserDetailsConverter;

/**
 * {@link AbstractOAuth2TokenAuthenticationToken} to {@link ArchUser}
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.3.3 13:57
 */
@Component
public class ArchOauth2TokenAuthenticationTokenToUserConverter implements AuthenticationToUserDetailsConverter {

    @Override
    public UserDetails convert(AbstractOAuth2TokenAuthenticationToken<OAuth2AccessToken> source) {
        // AbstractOAuth2TokenAuthenticationToken 必是登录用户, 直接获取当前用户信息.
        TokenInfo currentUser = SecurityUtils.getCurrentUser();
        ArchUser archUser = new ArchUser(source.getName(),
                                         "",
                                         currentUser.getIdentifierId(),
                                         currentUser.getAccountId(),
                                         currentUser.getTenantId(),
                                         currentUser.getLoginType(),
                                         currentUser.getNickName(),
                                         currentUser.getAvatar(),
                                         currentUser.getAuthorities());
        archUser.eraseCredentials();
        return archUser;

    }
}
