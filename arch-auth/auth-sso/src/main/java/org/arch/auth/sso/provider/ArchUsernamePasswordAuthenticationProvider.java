package org.arch.auth.sso.provider;

import org.arch.framework.ums.enums.ChannelType;
import org.arch.framework.ums.userdetails.ArchUser;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import top.dcenter.ums.security.core.api.service.UmsUserDetailsService;
import top.dcenter.ums.security.core.api.tenant.handler.TenantContextHolder;
import top.dcenter.ums.security.core.auth.provider.UsernamePasswordAuthenticationProvider;

/**
 * 自定义密码校验逻辑: 添加用户 {@link ChannelType} 类型校验, 非密码校验类型(PHONE/OAUTH2) 直接验证不通过
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.2.2 11:22
 */
@Component
public class ArchUsernamePasswordAuthenticationProvider extends UsernamePasswordAuthenticationProvider {

    public ArchUsernamePasswordAuthenticationProvider(PasswordEncoder passwordEncoder,
                                                      UmsUserDetailsService umsUserDetailsService,
                                                      TenantContextHolder tenantContextHolder) {
        super(passwordEncoder, umsUserDetailsService, tenantContextHolder);
    }

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        if (authentication.getCredentials() == null) {
            logger.debug("Authentication failed: no credentials provided");

            throw new BadCredentialsException(messages.getMessage(
                    "AbstractUserDetailsAuthenticationProvider.badCredentials",
                    "Bad credentials"));
        }

        String presentedPassword = authentication.getCredentials().toString();

        if (!(userDetails instanceof ArchUser))
        {
            logger.debug("Authentication failed: password does not match stored value");

            throw new BadCredentialsException(messages.getMessage(
                    "AbstractUserDetailsAuthenticationProvider.badCredentials",
                    "Bad credentials"));
        }

        ArchUser archUser = ((ArchUser) userDetails);
        if (!isValidChannelType(archUser) && !getPasswordEncoder().matches(presentedPassword,
                                                                         userDetails.getPassword())) {
            logger.debug("Authentication failed: password does not match stored value");

            throw new BadCredentialsException(messages.getMessage(
                    "AbstractUserDetailsAuthenticationProvider.badCredentials",
                    "Bad credentials"));
        }
    }

    private boolean isValidChannelType(ArchUser archUser) {
        // 是否是密码校验类型, 非密码校验类型(PHONE/OAUTH2) 则返回 false
        return ChannelType.ACCOUNT.equals(archUser.getChannelType())
               || ChannelType.EMAIL.equals(archUser.getChannelType());
    }
}
