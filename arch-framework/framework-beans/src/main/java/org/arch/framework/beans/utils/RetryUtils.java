package org.arch.framework.beans.utils;

import org.arch.framework.beans.event.RetryEvent;
import org.arch.framework.beans.exception.ArgumentException;
import org.arch.framework.beans.exception.constant.ArgumentStatuesCode;
import org.springframework.context.ApplicationContext;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;

import static java.util.Objects.isNull;


/**
 * 重试工具
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.3.2 14:29
 */
public class RetryUtils {

    /**
     * 发布重试事件
     * @param applicationContext    application context
     * @param traceId               调用链路追踪 id
     * @param retryObj              retry object
     * @param retryClz              retry class
     * @param methodName            method name
     * @param methodArgs            method args
     * @param retryArgs             retry args
     */
    public static void publishRetryEvent(@NonNull ApplicationContext applicationContext, @Nullable String traceId,
                                         @NonNull Object retryObj, @NonNull Class<?> retryClz,
                                         @NonNull String methodName, @Nullable Class<?>[] methodArgs,
                                         @Nullable Object... retryArgs) {
        Method method = ReflectionUtils.findMethod(retryClz,
                                                   methodName,
                                                   methodArgs);
        if (isNull(method)) {
        	throw new ArgumentException(ArgumentStatuesCode.VALID_ERROR);
        }
        applicationContext.publishEvent(new RetryEvent(retryObj,
                                                       traceId,
                                                       retryClz,
                                                       method,
                                                       retryArgs));
    }
}
