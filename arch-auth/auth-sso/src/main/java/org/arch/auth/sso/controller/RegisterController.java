package org.arch.auth.sso.controller;

import lombok.extern.slf4j.Slf4j;
import org.arch.auth.sso.request.bind.RegRequest;
import org.arch.auth.sso.username.service.UsernameKeywordFilteringService;
import org.arch.framework.beans.Response;
import org.arch.framework.ums.userdetails.ArchUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;
import top.dcenter.ums.security.core.api.service.UmsUserDetailsService;
import top.dcenter.ums.security.core.exception.RegisterUserFailureException;
import top.dcenter.ums.security.jwt.JwtContext;
import top.dcenter.ums.security.jwt.claims.service.GenerateClaimsSetService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static java.util.Objects.nonNull;
import static org.arch.auth.sso.userdetails.service.ArchUserDetailsServiceImpl.REG_REQUEST_PARAMETER_NAME;
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
    private final UsernameKeywordFilteringService usernameKeywordFilteringService;
    private final RememberMeServices rememberMeServices;
    private final GenerateClaimsSetService generateClaimsSetService;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public RegisterController(UmsUserDetailsService umsUserDetailsService,
                              UsernameKeywordFilteringService usernameKeywordFilteringService,
                              @Autowired(required = false)
                              RememberMeServices rememberMeServices,
                              GenerateClaimsSetService generateClaimsSetService) {
        this.umsUserDetailsService = umsUserDetailsService;
        this.usernameKeywordFilteringService = usernameKeywordFilteringService;
        this.rememberMeServices = rememberMeServices;
        this.generateClaimsSetService = generateClaimsSetService;
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
            regRequest.setNickName(nickName);
            ServletWebRequest servletWebRequest = new ServletWebRequest(request, response);
            servletWebRequest.setAttribute(REG_REQUEST_PARAMETER_NAME, regRequest, RequestAttributes.SCOPE_REQUEST);

            // 用户注册
            ArchUser archUser = (ArchUser) umsUserDetailsService.registerUser(servletWebRequest);

            // 执行用户登录逻辑
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(archUser, archUser.getPassword(), archUser.getAuthorities());
            login(request, response, authenticationToken);

            if (log.isInfoEnabled()) {
                log.info("用户注册成功: 租户: {}, 注册用户名: {}, aid: {}, channelType: {}",
                         archUser.getTenantId(), archUser.getUsername(), archUser.getAccountId(),
                         archUser.getChannelType().name());
            }

            return Response.success(null, "注册成功");

        }
        catch (RegisterUserFailureException e) {
            log.error(e.getMessage(), e);
            return Response.failed(e.getMessage());
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return Response.failed("用户注册失败");
        }
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
