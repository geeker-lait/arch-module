package org.arch.auth.sso.controller;

import org.arch.auth.sso.properties.SsoProperties;
import org.arch.ums.feign.account.client.UmsAccountOauthClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import top.dcenter.ums.security.common.enums.ErrorCodeEnum;
import top.dcenter.ums.security.common.vo.ResponseResult;

import javax.servlet.http.HttpServletRequest;
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
    @Autowired
    private UmsAccountOauthClient umsAccountOauthClient;

    public LoginController(RedisConnectionFactory redisConnectionFactory,
                           SsoProperties ssoProperties) {
        this.redisConnectionFactory = redisConnectionFactory;
        this.tempOauth2TokenPrefix = ssoProperties.getTempOauth2TokenPrefix();
        this.oauth2TokenParamName = ssoProperties.getOauth2TokenParamName();
    }

    /**
     * 登录页面
     * @return  跳转到登录页面
     */
    @RequestMapping(value = "/login", method = {RequestMethod.GET})
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/scopes/{appId}/{appCode}")
    public ResponseResult getScopes(@PathVariable("appId") String appId,
                                    @PathVariable("appCode") String appCode) {
        // TODO 测试
        return ResponseResult.success("", umsAccountOauthClient.getScopesByAppIdAndAppCode(appId, appCode));
    }

    /**
     * 第三方登录成功后 转发 到此接口来获取 token 与 refreshToken
     * @param model     {@link Model}
     * @param request   request
     * @return  转发到自动获取 token 与 refreshToken 的页面.
     */
    @RequestMapping(value = "/oauth2Token", method = {RequestMethod.GET})
    public String auth2Token(Model model, HttpServletRequest request) {
        String oauth2Token = (String) request.getSession().getAttribute(oauth2TokenParamName);
        model.addAttribute(oauth2TokenParamName, oauth2Token);
        return "oauth2Token";
    }

    /**
     * 根据 tokenKey 获取 token 与 refreshToken 的接口, 第三方登录专用.
     * @param tk    获取  token 与 refreshToken 的 tokenKey.
     * @return  返回  token 与 refreshToken.
     */
    @RequestMapping(value = "/oauth2Callback", method = {RequestMethod.POST})
    @ResponseBody
    public ResponseResult oAuth2LoginSuccessCallback(@RequestParam("tk") String tk) {
        if (hasText(tk)) {
            byte[] bytes = getConnection().get((tempOauth2TokenPrefix + tk).getBytes(StandardCharsets.UTF_8));
            if (nonNull(bytes)) {
                return ResponseResult.success(null, new String(bytes, StandardCharsets.UTF_8));
            }
        }
        return ResponseResult.fail(ErrorCodeEnum.NOT_FOUND);
    }

    @NonNull
    private RedisConnection getConnection() {
        return this.redisConnectionFactory.getConnection();
    }
}
