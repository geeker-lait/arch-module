package org.arch.framework.utils;

import org.arch.framework.beans.exception.ArgumentException;
import org.arch.framework.beans.exception.constant.ArgumentStatuesCode;
import org.arch.framework.event.RetryEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.ConcurrentReferenceHashMap;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.Map;

import static java.util.Objects.isNull;


/**
 * 重试工具
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.3.2 14:29
 */
public class RetryUtils {

    /**
     * Cache for {@link Class#getDeclaredMethods()} plus equivalent default methods
     * from Java 8 based interfaces, allowing for fast iteration.
     */
    private static final Map<Class<?>, Method[]> DECLARED_METHODS_CACHE = new ConcurrentReferenceHashMap<>(256);

    /**
     * 发布重试事件
     * @param applicationContext    application context
     * @param traceId               调用链路追踪 id
     * @param retryObj              retry object
     * @param retryClz              retry class
     * @param methodName            method name, 方法必须幂等性
     * @param methodArgs            method args
     * @param retryArgs             retry args
     */
    public static void publishRetryEvent(@NonNull ApplicationContext applicationContext, @Nullable String traceId,
                                         @NonNull Object retryObj, @NonNull Class<?> retryClz,
                                         @NonNull String methodName, @NonNull Class<?>[] methodArgs,
                                         @Nullable Object... retryArgs) {
        Method[] methods = getMethods(retryClz);
        Method method = findMethod(methods, retryClz, methodName, methodArgs);
        if (isNull(method)) {
        	throw new ArgumentException(ArgumentStatuesCode.VALID_ERROR);
        }
        applicationContext.publishEvent(new RetryEvent(retryObj,
                                                       traceId,
                                                       retryClz,
                                                       method,
                                                       retryArgs));
    }

    @Nullable
    private static Method findMethod(@NonNull Method[] methods,
                                     @NonNull Class<?> retryClz,
                                     @NonNull String methodName,
                                     @NonNull Class<?>[] methodArgs) {
        for (Method method : methods) {
            Class<?> declaringClass = method.getDeclaringClass();
            boolean isClassMatch = declaringClass.equals(retryClz) || declaringClass.isAssignableFrom(retryClz);
            if (isClassMatch && method.getName().equals(methodName)) {
                Class<?>[] parameterTypes = method.getParameterTypes();
                int parameterClassLen = parameterTypes.length;
                int methodArgsClassLen = methodArgs.length;
                if (parameterClassLen != methodArgsClassLen) {
                    continue;
                }
                //noinspection ConstantConditions
                if (parameterClassLen == 0 && methodArgsClassLen == 0) {
                    return method;
                }
                Class<?> parameterType, methodArg;
                boolean isArgsMatch = true;
                for (int i = 0; i < parameterClassLen; i++) {
                    parameterType = parameterTypes[i];
                    methodArg = methodArgs[i];
                    boolean isArgClassMatch = parameterType.equals(methodArg) || parameterType.isAssignableFrom(methodArg);
                    if (!isArgClassMatch) {
                        isArgsMatch = false;
                        break;
                    }
                }
                if (isArgsMatch) {
                    return method;
                }
            }
        }
        return null;
    }

    @NonNull
    private static Method[] getMethods(@NonNull Class<?> retryClz) {
        Method[] methods;
        methods = DECLARED_METHODS_CACHE.get(retryClz);
        if (isNull(methods)) {
            methods = ReflectionUtils.getUniqueDeclaredMethods(retryClz);
            DECLARED_METHODS_CACHE.put(retryClz, methods);
        }
        return methods;
    }
}
