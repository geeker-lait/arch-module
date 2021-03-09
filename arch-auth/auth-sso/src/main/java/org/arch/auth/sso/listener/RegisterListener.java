package org.arch.auth.sso.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.auth.sso.recommend.service.RecommendAndPromotionService;
import org.arch.framework.event.RegisterEvent;
import org.arch.framework.ums.userdetails.ArchUser;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;
import static org.arch.auth.sso.utils.RegisterUtils.getTraceId;
import static org.arch.framework.utils.RetryUtils.publishRetryEvent;

/**
 * 注册事件监听器
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.3.6 12:57
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class RegisterListener implements ApplicationListener<RegisterEvent>, ApplicationContextAware {

    private final RecommendAndPromotionService recommendAndPromotionService;

    private ApplicationContext applicationContext;

    @Override
    public void onApplicationEvent(@NonNull RegisterEvent event) {
        ArchUser archUser = event.getArchUser();
        // 记录日志
        log.info("用户注册成功: 租户: {}, identifier: {}, aid: {}, channelType: {}, source: {}",
                 archUser.getTenantId(), archUser.getUsername(), archUser.getAccountId(),
                 archUser.getChannelType().name(), event.getSource());

        // 用户推荐 或 推广统计
        userRecommendationOrPromotion(event.getSource());

    }

    private void userRecommendationOrPromotion(String source) {
        if (isNull(source)) {
        	return;
        }
        String decodeRecommendationOrPromotionCode =
                recommendAndPromotionService.decodeRecommendationOrPromotionCode(source);
        Boolean isSuccess =
                recommendAndPromotionService.userRecommendOrPromotionHandler(decodeRecommendationOrPromotionCode);
        if (!isSuccess) {
            String traceId = getTraceId();
            log.warn("用户推荐 或 推广统计处理失败, 发布重试事件, traceId={}", traceId);
            publishRetryEvent(this.applicationContext, traceId,
                              this.recommendAndPromotionService,
                              RecommendAndPromotionService.class,
                              "userRecommendOrPromotionHandler",
                              new Class[] {String.class},
                              decodeRecommendationOrPromotionCode);
        }
    }

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
