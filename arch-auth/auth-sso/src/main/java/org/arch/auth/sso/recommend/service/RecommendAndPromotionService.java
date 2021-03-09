package org.arch.auth.sso.recommend.service;

import org.arch.framework.ums.userdetails.ArchUser;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * 推广与用户推荐服务
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.3.8 23:01
 */
public interface RecommendAndPromotionService {

    /**
     * 用户推荐码的前缀, 默认: user_ .
     * 如果用户 ID 为 001, 则-用户的推荐类型为: user_001
     */
    String USER_RECOMMEND_SOURCE_PREFIX = "user_";

    /**
     * 生成用户推荐码, 只有在用户登录情况下才会生成, 未登录情况下生成返回 null.
     * @return  返回用户推荐码, 未登录情况下生成返回 null.
     */
    @Nullable
    default String generateUserRecommendCode() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken)) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof ArchUser)
            {
                ArchUser archUser = ((ArchUser) principal);
                return USER_RECOMMEND_SOURCE_PREFIX.concat(archUser.getAccountId().toString());
            }
        }
        return null;
    }

    /**
     * 根据渠道生成推广码
     * @param channel 渠道
     * @return  返回渠道推广码字符串
     */
    @NonNull
    String generatePromotionCode(@NonNull String channel);

    /**
     * 解码 推广或用户推荐码
     * @param recommendationOrPromotionCode    推广或用户推荐码
     * @return  解码后 推广或用户推荐码
     */
    @NonNull
    String decodeRecommendationOrPromotionCode(@NonNull String recommendationOrPromotionCode);

    /**
     * 推广处理或用户推荐处理, 方法实现必须幂等性
     * @param decodeRecommendationOrPromotionCode   解码后的推广码或用户推荐码
     * @return  是否成功处理
     */
    @NonNull
    Boolean userRecommendOrPromotionHandler(@NonNull String decodeRecommendationOrPromotionCode);

}
