package org.arch.auth.sso.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import org.arch.auth.sso.properties.ArchSsoProperties;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
@Api(value = "登录相关")
public class LoginController {

    private final RedisConnectionFactory redisConnectionFactory;
    private final ArchSsoProperties archSsoProperties;

    public LoginController(RedisConnectionFactory redisConnectionFactory,
                           ArchSsoProperties archSsoProperties) {
        this.redisConnectionFactory = redisConnectionFactory;
        this.archSsoProperties = archSsoProperties;
    }

    @ApiOperation(value = "跳转登录页", httpMethod = "GET")
    @RequestMapping(value = "/login", method = {RequestMethod.GET})
    public String login() {
        return "login";
    }

    @ApiOperation(value = "第三方登录成功后自动转发到此 API, 以便自动获取 token")
    @RequestMapping(value = "/oauth2Token", method = {RequestMethod.GET})
    public String auth2Token(Model model, HttpServletRequest request) {
        String oauth2Token = (String) request.getSession().getAttribute(archSsoProperties.getOauth2TokenParamName());
        model.addAttribute(archSsoProperties.getOauth2TokenParamName(), oauth2Token);
        return "oauth2Token";
    }

    @ApiOperation(value = "第三方登录成功后, 获取 token 的 API")
    @ApiResponse(code = 200, response = ResponseResult.class, message = "响应的详细数据放在 data 中, code==0, 表示访问成功")
    @RequestMapping(value = "/oauth2Callback", method = {RequestMethod.POST})
    @ResponseBody
    public ResponseResult oAuth2LoginSuccessCallback(@ApiParam(name = "tk", value = "接收的参数名称", required = true)
                                                     @RequestParam("tk") String tk) {
        if (hasText(tk)) {
            byte[] bytes = getConnection().get((archSsoProperties.getTempOauth2TokenPrefix() + tk).getBytes(StandardCharsets.UTF_8));
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
