package org.arch.auth.sso.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.auth.sso.recommend.service.RecommendAndPromotionService;
import org.arch.framework.beans.Response;
import org.arch.framework.beans.exception.constant.AuthStatusCode;
import org.arch.framework.beans.exception.constant.CommonStatusCode;
import org.arch.framework.ums.bean.TokenInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static java.util.Objects.isNull;

/**
 * 用户推荐控制器
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.3.14 17:53
 */
@RestController
@RequestMapping("/recommend")
@Slf4j
@RequiredArgsConstructor
public class RecommendController {

    private final RecommendAndPromotionService recommendAndPromotionService;

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
