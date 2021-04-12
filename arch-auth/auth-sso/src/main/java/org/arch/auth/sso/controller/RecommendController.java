package org.arch.auth.sso.controller;

import lombok.extern.slf4j.Slf4j;
import org.arch.auth.sso.properties.SsoProperties;
import org.arch.auth.sso.recommend.service.RecommendAndPromotionService;
import org.arch.framework.beans.Response;
import org.arch.framework.beans.exception.constant.AuthStatusCode;
import org.arch.framework.beans.exception.constant.CommonStatusCode;
import org.arch.framework.ums.bean.TokenInfo;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.dcenter.ums.security.core.auth.properties.ClientProperties;
import top.dcenter.ums.security.core.oauth.properties.Auth2Properties;

import static java.util.Objects.isNull;
import static org.springframework.util.StringUtils.hasText;

/**
 * 用户推荐控制器
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.3.14 17:53
 */
@RestController
@RequestMapping("/recommend")
@Slf4j
public class RecommendController {

    private final RecommendAndPromotionService recommendAndPromotionService;
    /**
     * Http(s)://www.xxx.xxx/contextPath/loginPath?_from=
     */
    private final String registerUrl;

    public RecommendController(RecommendAndPromotionService recommendAndPromotionService,
                               Environment environment,
                               Auth2Properties auth2Properties,
                               ClientProperties clientProperties,
                               SsoProperties ssoProperties) {
        this.recommendAndPromotionService = recommendAndPromotionService;
        String contextPath = environment.getProperty("server.servlet.contextPath");
        if (!hasText(contextPath)) {
            contextPath = "";
        }
        this.registerUrl = auth2Properties.getDomain() + contextPath + clientProperties.getLoginPage()
                + "?" + ssoProperties.getSourceParameterName() + "=";
    }

    /**
     * 获取带有用户推荐码的注册链接
     * @param token {@link TokenInfo}
     * @return  返回带有用户推荐码的注册链接
     */
    @GetMapping("/registerUrl")
    public Response<String> getRegisterUrlWithRecommendCode(TokenInfo token) {
        if (isNull(token)) {
            return Response.failed(AuthStatusCode.UNAUTHORIZED);
        }
        try {
            return Response.success(this.registerUrl + this.recommendAndPromotionService.generateUserRecommendCode());
        }
        catch (Exception e) {
            log.error("生成用户推荐码失败", e);
            return Response.failed(CommonStatusCode.GENERATE_USER_RECOMMEND_CODE_FAILED);
        }
    }

    /**
     * 获取用户推荐码
     * @param token {@link TokenInfo}
     * @return  返回用户推荐码
     */
    @GetMapping("/code")
    public Response<String> getRecommendCode(TokenInfo token) {
        if (isNull(token)) {
            return Response.failed(AuthStatusCode.UNAUTHORIZED);
        }
        try {
            return Response.success(this.recommendAndPromotionService.generateUserRecommendCode());
        }
        catch (Exception e) {
            log.error("生成用户推荐码失败", e);
            return Response.failed(CommonStatusCode.GENERATE_USER_RECOMMEND_CODE_FAILED);
        }
    }

}
