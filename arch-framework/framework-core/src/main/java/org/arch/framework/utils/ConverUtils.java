package org.arch.framework.utils;

import org.springframework.beans.BeanUtils;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 *
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.5.16 14:23
 */
public class ConverUtils {

    /**
     * 拷贝属性.
     * @see BeanUtils#copyProperties(Object, Object)
     * @param source    源对象
     * @param clz       目标类型的 Class, 必须带有无参构造器, 否则抛出 {@link RuntimeException}
     * @param <T>       目标类型
     * @return  拷贝了源对象的目标类型的对象, 当 clz 没有无参构造器时, 抛出 {@link RuntimeException}
     */
    @NonNull
    public static <T> T copyProperties(@NonNull Object source, @NonNull Class<T> clz) {
        try {
            T target = clz.newInstance();
            BeanUtils.copyProperties(source, target);
            return target;
        }
        catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
