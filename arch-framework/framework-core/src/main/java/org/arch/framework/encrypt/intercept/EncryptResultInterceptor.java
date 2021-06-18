package org.arch.framework.encrypt.intercept;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.arch.framework.encrypt.DelegatingEncryptHandler;
import org.arch.framework.encrypt.EncryptClass;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.reflect.Field;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 数据库返回值解密拦截器
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.5.20 20:31
 */
@Intercepts({
        @Signature(type = ResultSetHandler.class, method = "handleResultSets", args={Statement.class})
})
@Slf4j
public class EncryptResultInterceptor implements Interceptor {

    private final DelegatingEncryptHandler encryptHandler;

    public EncryptResultInterceptor(DelegatingEncryptHandler encryptHandler) {
        this.encryptHandler = encryptHandler;
    }
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object result = invocation.proceed();
        if (Objects.isNull(result)){
            return null;
        }

        if (result instanceof ArrayList) {
            //noinspection unchecked
            List<Object> resultList = (ArrayList<Object>) result;
            if (CollectionUtils.isNotEmpty(resultList) && needToDecrypt(resultList.get(0))){
                for (Object resultObj : resultList) {
                    Field[] declaredFields = resultObj.getClass().getDeclaredFields();
                    this.encryptHandler.decode(declaredFields, resultObj);
                }
            }
        } else {
            if (needToDecrypt(result)){
                Field[] declaredFields = result.getClass().getDeclaredFields();
                this.encryptHandler.decode(declaredFields, result);
            }
        }
        return result;
    }

    public boolean needToDecrypt(Object object){
        Class<?> objectClass = object.getClass();
        EncryptClass encryptClass = AnnotationUtils.findAnnotation(objectClass, EncryptClass.class);
        return Objects.nonNull(encryptClass);
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

}
