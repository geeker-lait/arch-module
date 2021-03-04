package org.arch.auth.sso.controller;

import org.arch.auth.sso.properties.SsoProperties;
import org.arch.framework.beans.Response;
import org.arch.framework.beans.exception.constant.AuthStatusCode;
import org.arch.framework.utils.SecurityUtils;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.lang.NonNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import top.dcenter.ums.security.core.vo.AuthTokenVo;
import top.dcenter.ums.security.jwt.JwtContext;

import java.nio.charset.StandardCharsets;

import static java.util.Objects.nonNull;
import static org.springframework.util.StringUtils.hasText;

/**
 * 登录控制器
 *
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.1.5 11:54
 */
@Controller
public class LoginController {

    private final RedisConnectionFactory redisConnectionFactory;
    private final String tempOauth2TokenPrefix;
    private final String oauth2TokenParamName;
    private final String delimiterOfTokenAndRefreshToken;

    public LoginController(RedisConnectionFactory redisConnectionFactory,
                           SsoProperties ssoProperties) {
        this.redisConnectionFactory = redisConnectionFactory;
        this.tempOauth2TokenPrefix = ssoProperties.getTempOauth2TokenPrefix();
        this.oauth2TokenParamName = ssoProperties.getOauth2TokenParamName();
        this.delimiterOfTokenAndRefreshToken = ssoProperties.getDelimiterOfTokenAndRefreshToken();
    }

    /**
     * 登录页面
     * @return  跳转到登录页面
     */
    @RequestMapping(value = "/login", method = {RequestMethod.GET})
    public String login() {
        return "login";
    }

    /**
     * 登出
     * @return  跳转到登录页面
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout() {
        JwtContext.addReAuthFlag(SecurityUtils.getCurrentUserId().toString());
        SecurityContextHolder.clearContext();
        return "login";
    }

    /**
     * 第三方登录成功后 转发 到此接口来获取 token 与 refreshToken
     * @param tk        获取  token 与 refreshToken 的 tokenKey.
     * @param username  账号标识名称
     * @param id        账号标识 id
     * @param model     model
     * @return  转发到自动获取 token 与 refreshToken 的页面.
     */
    @RequestMapping(value = "/oauth2Token", method = {RequestMethod.GET})
    public String auth2Token(@RequestParam("tk") String tk,
                             @RequestParam("username") String username,
                             @RequestParam("id") String id, Model model) {
        model.addAttribute(oauth2TokenParamName, tk);
        model.addAttribute("username", username);
        model.addAttribute("id", id);
        return "oauth2Token";
    }

    /**
     * 根据 tokenKey 获取 token 与 refreshToken 的接口, 第三方登录专用.
     * @param tk        获取  token 与 refreshToken 的 tokenKey.
     * @param username  账号标识名称
     * @param id        账号标识 id
     * @return  返回  token 与 refreshToken.
     */
    @RequestMapping(value = "/oauth2Callback", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Response<AuthTokenVo> oAuth2LoginSuccessCallback(@RequestParam("tk") String tk,
                                                            @RequestParam("username") String username,
                                                            @RequestParam("id") String id) {
        if (hasText(tk)) {
            byte[] bytes = getConnection().get((tempOauth2TokenPrefix + tk).getBytes(StandardCharsets.UTF_8));
            if (nonNull(bytes)) {
                // tokenInfo = jwtToken#@#refreshToken#@#url 或 tokenInfo = jwtToken#@#url
                String tokenInfo = new String(bytes, StandardCharsets.UTF_8);
                if (!hasText(tokenInfo)) {
                    return Response.failed(AuthStatusCode.UNAUTHORIZED);
                }
                String[] split = tokenInfo.split(delimiterOfTokenAndRefreshToken);
                int length = split.length;
                AuthTokenVo authTokenVo = new AuthTokenVo();
                authTokenVo.setId(id);
                authTokenVo.setUsername(username);
                authTokenVo.setToken(split[0]);
                authTokenVo.setTargetUrl(split[length - 1]);
                if (length - 1 != 1) {
                    authTokenVo.setRefreshToken(split[1]);
                }
                return Response.success(authTokenVo, "成功获取 token");
            }
        }
        return Response.failed(AuthStatusCode.UNAUTHORIZED);
    }

    @NonNull
    private RedisConnection getConnection() {
        return this.redisConnectionFactory.getConnection();
    }
}
