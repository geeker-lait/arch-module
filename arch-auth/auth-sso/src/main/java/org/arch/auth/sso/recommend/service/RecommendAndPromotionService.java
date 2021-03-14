package org.arch.auth.sso.recommend.service;

import org.arch.framework.beans.exception.AuthenticationException;
import org.arch.framework.ums.enums.SourceType;
import org.springframework.lang.NonNull;

/**
 * 推广与用户推荐服务
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.3.8 23:01
 */
public interface RecommendAndPromotionService {

    /**
     * 生成用户推荐码, 只有在用户登录情况下才会生成, 未登录情况下抛出 {@link AuthenticationException}
     * // 002_3_48: 00 为 SourceType 业务前缀, 2_3_48 为 rsOrg_rsDeep_rsSeq(rs=Relationship)
     * @return  返回用户推荐码(002_3_48_44).
     */
    @NonNull
    String generateUserRecommendCode();

    /**
     * 根据渠道生成推广码, 用于 {@link SourceType#ARCH} 生成推广码
     * @param sourceType 渠道 {@link SourceType}
     * @return  返回渠道推广码字符串(PromotionPrefix_UUID)
     */
    @NonNull
    String generatePromotionCode(@NonNull SourceType sourceType);

    /**
     * 加密 推广或用户推荐码
     * @param recommendationOrPromotionCode    推广或用户推荐码
     * @return  加密后的推广或用户推荐码
     */
    @NonNull
    String encodeRecommendationOrPromotionCode(@NonNull String recommendationOrPromotionCode);

    /**
     * 解码 推广或用户推荐码
     * @param recommendationOrPromotionCode    推广或用户推荐码
     * @return  解码后的推广或用户推荐码
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
