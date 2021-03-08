package org.arch.auth.sso.listener;

import lombok.extern.slf4j.Slf4j;
import org.arch.auth.sso.properties.RetryProperties;
import org.arch.framework.beans.Response;
import org.arch.framework.event.RetryEvent;
import org.slf4j.MDC;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import top.dcenter.ums.security.common.executor.DefaultThreadFactory;
import top.dcenter.ums.security.common.executor.MdcScheduledThreadPoolExecutor;

import java.lang.reflect.Method;
import java.util.Objects;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static java.util.Objects.isNull;
import static org.springframework.util.StringUtils.hasText;
import static top.dcenter.ums.security.common.consts.MdcConstants.MDC_KEY;

/**
 * 重试事件监听器
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.3.2 14:54
 */
@Component
@Slf4j
public class RetryListener implements ApplicationListener<RetryEvent> {

    private final ApplicationEventPublisher publisher;
    private final MdcScheduledThreadPoolExecutor scheduledThreadPoolExecutor;
    private final TimeUnit timeUnit;
    private final Integer delay;
    private final Integer maxAttempts;

    public RetryListener(ApplicationEventPublisher publisher, RetryProperties retryProperties) {
        this.publisher = publisher;
        this.timeUnit = retryProperties.getTimeUnit();
        this.delay = retryProperties.getDelay();
        this.maxAttempts = retryProperties.getMaxAttempts();
        scheduledThreadPoolExecutor =
                new MdcScheduledThreadPoolExecutor(Runtime.getRuntime().availableProcessors(),
                                                       new DefaultThreadFactory("sso-retry-listener"),
                                                       new ThreadPoolExecutor.AbortPolicy());
    }

    @Override
    public void onApplicationEvent(@NonNull RetryEvent event) {
        int retryNo = event.getRetryNo();
        // 重试失败, 记录日志, 定时任务 或 人工处理
        if (retryNo >= maxAttempts) {
            log.error("重试 {} 次失败: {}", retryNo, event.toString());
            return;
        }

        try {
            // retry
            Method retryMethod = event.getRetryMethod();
            ScheduledFuture<Object> future = scheduledThreadPoolExecutor.schedule(
                    () -> {
                        // MDC traceId
                        String traceId = event.getTraceId();
                        if (hasText(traceId)) {
                            MDC.put(MDC_KEY, traceId);
                        }
                        return retryMethod.invoke(event.getSource(), event.getRetryArgs());
                    },
                    delay * (1L + retryNo),
                    timeUnit
            );

            // 检查是否重试成功
            Object result = future.get();
            if (isNull(result) && isNotVoidOfReturnType(retryMethod)) {
                reset(event);
            }
            else if (isResponseOfReturnType(retryMethod)) {
                Response<?> response = (Response<?>) result;
                Object successData = response.getSuccessData();
                if (isNull(successData) || isFalse(successData)) {
                    reset(event);
                }
            }
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            reset(event);
        }

    }

    private void reset(@NonNull RetryEvent event) {
        event.addRetryNo();
        this.publisher.publishEvent(event);
    }

    private static boolean isNotVoidOfReturnType(@NonNull Method method) {
        return !Objects.equals("void",method.getReturnType().getName());
    }

    private static boolean isResponseOfReturnType(@NonNull Method method) {
        return method.getReturnType().isAssignableFrom(Response.class);
    }

    @NonNull
    private static Boolean isFalse(@NonNull Object obj) {
        return obj instanceof Boolean ? !((Boolean) obj) : Boolean.FALSE;
    }

}
