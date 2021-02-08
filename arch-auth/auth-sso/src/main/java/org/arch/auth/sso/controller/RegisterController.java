package org.arch.auth.sso.controller;

import lombok.extern.slf4j.Slf4j;
import org.arch.auth.sso.properties.SsoProperties;
import org.arch.auth.sso.request.bind.RegRequest;
import org.arch.auth.sso.username.service.UsernameKeywordFilteringService;
import org.arch.auth.sso.utils.RegisterUtils;
import org.arch.framework.beans.Response;
import org.arch.framework.id.IdService;
import org.arch.framework.ums.enums.AccountType;
import org.arch.framework.ums.enums.ChannelType;
import org.arch.framework.ums.userdetails.ArchUser;
import org.arch.ums.account.dto.AuthLoginDto;
import org.arch.ums.account.dto.AuthRegRequest;
import org.arch.ums.feign.account.client.UmsAccountClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import top.dcenter.ums.security.core.api.service.UmsUserDetailsService;
import top.dcenter.ums.security.core.api.tenant.handler.TenantContextHolder;
import top.dcenter.ums.security.jwt.JwtContext;
import top.dcenter.ums.security.jwt.claims.service.GenerateClaimsSetService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.springframework.util.StringUtils.hasText;

/**
 * 注册相关接口
 * @author YongWu zheng
 * @since 2021.1.3 16:25
 */
@RestController
@Slf4j
@RequestMapping("/reg")
public class RegisterController {

    private final UmsUserDetailsService umsUserDetailsService;
    private final UmsAccountClient umsAccountClient;
    private final GenerateClaimsSetService generateClaimsSetService;
    private final RememberMeServices rememberMeServices;
    private final IdService idService;
    private final SsoProperties ssoProperties;
    private final TenantContextHolder tenantContextHolder;
    private final PasswordEncoder passwordEncoder;
    private final UsernameKeywordFilteringService usernameKeywordFilteringService;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public RegisterController(UmsUserDetailsService umsUserDetailsService,
                              UmsAccountClient umsAccountClient,
                              GenerateClaimsSetService generateClaimsSetService,
                              @Autowired(required = false) RememberMeServices rememberMeServices,
                              IdService idService,
                              SsoProperties ssoProperties,
                              TenantContextHolder tenantContextHolder,
                              PasswordEncoder passwordEncoder,
                              UsernameKeywordFilteringService usernameKeywordFilteringService) {
        this.umsUserDetailsService = umsUserDetailsService;
        this.umsAccountClient = umsAccountClient;
        this.generateClaimsSetService = generateClaimsSetService;
        this.rememberMeServices = rememberMeServices;
        this.idService = idService;
        this.ssoProperties = ssoProperties;
        this.tenantContextHolder = tenantContextHolder;
        this.passwordEncoder = passwordEncoder;
        this.usernameKeywordFilteringService = usernameKeywordFilteringService;
    }

    /**
     * 用户名密码注册请求
     * @param regRequest   注册请求
     * @return  返回是否注册成功信息
     */
    @RequestMapping(value = "/form", method = {RequestMethod.POST})
    public Response<String> register(@Validated RegRequest regRequest, HttpServletRequest request,
                                     HttpServletResponse response) {
        try {
            // 获取昵称且校验用户名与昵称是否有效
            String nickName = getNickNameAndCheck(regRequest);
            if (!hasText(nickName)) {
            	return Response.failed("用户名或昵称不规范");
            }
            // 获取注册的账户类型
            AccountType accountType = RegisterUtils.getAccountType(ssoProperties.getAccountTypeParameterName());
            if (isNull(accountType)) {
                return Response.failed("没有传递 AccountType 参数 或 格式错误");
            }
            // 查询用户是否已被注册
            List<Boolean> exists = umsUserDetailsService.existedByUsernames(regRequest.getUsername());
            if (isNull(exists) || exists.get(0)) {
                return Response.failed("用户名已被注册");
            }

            // 用户注册
            AuthRegRequest authRegRequest = getAuthRegRequest(regRequest, nickName, accountType);
            Response<AuthLoginDto> loginDtoResponse = umsAccountClient.register(authRegRequest);
            AuthLoginDto authLoginDto = loginDtoResponse.getSuccessData();
            if (isNull(authLoginDto)) {
                return Response.failed("用户注册失败");
            }

            // 用户注册成功转换为 UserDetails
            final ArchUser archUser = new ArchUser(authLoginDto.getIdentifier(),
                                                   authLoginDto.getCredential(),
                                                   authLoginDto.getAid(),
                                                   authLoginDto.getTenantId(),
                                                   authLoginDto.getChannelType(),
                                                   authLoginDto.getNickName(),
                                                   authLoginDto.getAvatar(),
                                                   true,
                                                   true,
                                                   true,
                                                   true,
                                                   AuthorityUtils.commaSeparatedStringToAuthorityList(
                                                           authLoginDto.getAuthorities()));
            // 执行用户登录逻辑
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(archUser,
                                                            authLoginDto.getCredential(),
                                                            archUser.getAuthorities());
            login(request, response, authenticationToken);

            if (log.isInfoEnabled()) {
                log.info("用户注册成功: 租户: {}, 注册用户名: {}, aid: {}, accountType: {}, source: {}",
                         authLoginDto.getTenantId(), authLoginDto.getIdentifier(), authRegRequest.getAid(),
                         accountType.name(), authRegRequest.getSource());
            }

            return Response.success(null, "注册成功");

        }
        catch (IOException e) {
            log.error(e.getMessage(), e);
            return Response.failed("用户名已被注册");
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return Response.failed("用户注册失败");
        }
    }

    private AuthRegRequest getAuthRegRequest(RegRequest regRequest, String nickName, AccountType accountType) {
        // 获取头像
        String avatar = regRequest.getAvatar();
        if (!hasText(avatar)) {
            avatar = ssoProperties.getDefaultAvatar();
        }
        // 获取推荐信息
        String source = RegisterUtils.getSource(ssoProperties.getSourceParameterName());
        // 获取租户 ID
        String tenantId = tenantContextHolder.getTenantId();
        // 构建默认的用户权限
        String authorities = RegisterUtils.getDefaultAuthorities(ssoProperties, tenantId);


        return AuthRegRequest.builder()
                             .aid(Long.valueOf(idService.generateId(accountType.getIdKey())))
                             .tenantId(Integer.valueOf(tenantId))
                             .identifier(regRequest.getUsername())
                             .credential(passwordEncoder.encode(regRequest.getPassword()))
                             .authorities(authorities)
                             .avatar(avatar)
                             .channelType(ChannelType.ACCOUNT)
                             .nickName(nickName)
                             .source(source)
                             .build();
    }

    /**
     * 获取昵称且对用户名与昵称进行有效性校验
     * @param regRequest    {@link RegRequest}
     * @return 返回昵称, 如果用户名或昵称校验无效返回 null.
     */
    @Nullable
    private String getNickNameAndCheck(@NonNull RegRequest regRequest) {
        if (!usernameKeywordFilteringService.isValid(regRequest.getUsername())) {
            return null;
        }

        String nickName = regRequest.getNickName();
        if (hasText(nickName)) {
            if (hasText(nickName) && !usernameKeywordFilteringService.isValid(nickName)) {
                return null;
            }
        }
        else {
            nickName = regRequest.getUsername();
        }
        return nickName;
    }

    private void login(HttpServletRequest request, HttpServletResponse response,
                       UsernamePasswordAuthenticationToken authenticationToken) {
        /* jwt token 与 refresh token 设置到指定的请求头, 相关参数请查看 ums.jwt.bearer 下的相关属性设置
           如果是: jwt + session 模式, 用户的 TokenInfo 信息会保存到 redis 中
         */
        Authentication jwtAuthenticationToken =
                JwtContext.createJwtAndToJwtAuthenticationToken(authenticationToken, generateClaimsSetService);
        SecurityContextHolder.getContext().setAuthentication(jwtAuthenticationToken);
        if (nonNull(rememberMeServices)) {
            rememberMeServices.loginSuccess(request, response, jwtAuthenticationToken);
        }
    }
}
