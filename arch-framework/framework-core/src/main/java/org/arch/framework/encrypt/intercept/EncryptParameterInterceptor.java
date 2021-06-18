package org.arch.framework.encrypt.intercept;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.arch.framework.encrypt.DelegatingEncryptHandler;
import org.arch.framework.encrypt.EncryptClass;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.util.Objects;

/**
 * 数据库参数加密拦截器
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.5.20 20:31
 */
@Intercepts({
        @Signature(type = ParameterHandler.class, method = "setParameters", args = PreparedStatement.class),
})
@Slf4j
public class EncryptParameterInterceptor implements Interceptor {

    private final DelegatingEncryptHandler encryptHandler;

    public EncryptParameterInterceptor(DelegatingEncryptHandler encryptHandler) {
        this.encryptHandler = encryptHandler;
    }

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        log.debug("拦截器ParamInterceptor");
        //拦截 ParameterHandler 的 setParameters 方法 动态设置参数
        if (invocation.getTarget() instanceof ParameterHandler) {
            ParameterHandler parameterHandler = (ParameterHandler) invocation.getTarget();
            PreparedStatement ps = (PreparedStatement) invocation.getArgs()[0];

            // 反射获取 参数对像
            Field parameterField =
                    parameterHandler.getClass().getDeclaredField("parameterObject");
            parameterField.setAccessible(true);
            Object parameterObject = parameterField.get(parameterHandler);
            if (Objects.nonNull(parameterObject)){
                Class<?> parameterObjectClass = parameterObject.getClass();
                EncryptClass encryptClass = AnnotationUtils.findAnnotation(parameterObjectClass, EncryptClass.class);
                if (Objects.nonNull(encryptClass)){
                    Field[] declaredFields = parameterObjectClass.getDeclaredFields();
                    this.encryptHandler.encode(declaredFields, parameterObject);
                }
            }
        }
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

}
