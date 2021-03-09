package org.arch.auth.sso.recommend.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

/**
 * 推广与用户推荐服务实现
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.3.8 23:59
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class RecommendAndPromotionServiceImpl implements RecommendAndPromotionService {

    @Nullable
    @Override
    public String generateUserRecommendCode() {
        // TODO
        return "null";
    }

    @NonNull
    @Override
    public String generatePromotionCode(@NonNull String channel) {
        // TODO
        return "null";
    }

    @NonNull
    @Override
    public String decodeRecommendationOrPromotionCode(@NonNull String recommendationOrPromotionCode) {
        // TODO
        return "null";
    }

    @NonNull
    @Override
    public Boolean userRecommendOrPromotionHandler(@NonNull String decodeRecommendationOrPromotionCode) {
        // TODO
        /*
            唯一索引: tenantId + pid + deep + seq
            当前位置: A10[DEEP, SEQ] pseq=2, id=10, pid=2
            查找上级: A02[DEEP-1, PSEQ], 条件: pid=2, deep=1-1=0, seq=2
            查找下级: pid=10,deep=1+1=2
         */
        return false;
    }
}
