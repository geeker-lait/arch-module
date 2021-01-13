package org.arch.framework.crud.utils;

import io.beanmapper.BeanMapper;
import io.beanmapper.core.BeanPropertyMatch;
import io.beanmapper.core.converter.BeanConverter;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/***
 * 实现将字符串类型的数字或数字转成对应的枚举类型
 *
 */
public class EnumConverter implements BeanConverter {

    /***
     * 将字符串或数字转成枚举<br>
     * 如果source本身为null，不会触发该方法，这是由框架决定的<br>
     * 先判断传进来的是否为数字。时数字后才转成对应的枚举<br>
     * 不是数字的话，无法创建枚举，当然也可以创建默认的枚举，但是不建议这样做。<br>
     * 原因很简单，默认枚举可能会影响后续的业务处理。<br>
     * 所有不是数字的话直接返回null
     */
    @Override
    public Object convert(BeanMapper beanMapper, Object source, Class<?> targetClass,
                          BeanPropertyMatch beanPropertyMatch) {

        String sourceStr = source.toString();
        if (StringUtils.isNumeric(sourceStr)) {
            return targetClass.getEnumConstants()[Integer.parseInt(sourceStr)];
        }

        return null;
    }

    /**
     * 判断依据，目标字段（targetClass）为枚举字段<br>
     * 并且来源字段（sourceClass）为字符串或数字时，判断成立
     */
    @Override
    public boolean match(Class<?> sourceClass, Class<?> targetClass) {
        return targetClass.isEnum()
                && (Objects.equals(String.class, sourceClass) || Objects.equals(Integer.class, sourceClass));
    }

}
