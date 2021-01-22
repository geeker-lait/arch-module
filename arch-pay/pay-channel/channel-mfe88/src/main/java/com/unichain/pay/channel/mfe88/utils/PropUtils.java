package com.unichain.pay.channel.mfe88.utils;

import cn.hutool.core.bean.BeanUtil;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.*;

/**
 * @create 2019-08-09 13:13
 */
public class PropUtils {

    /**
     * 初始化参数中对象的每个为空的属性
     *
     * @time 13:58
     * @params
     */
    public static <T> void initClassProp(T bean) {
        Class<?> beanClass = bean.getClass();
        Field[] declaredFields = beanClass.getDeclaredFields();
        for (int i = 0; i < declaredFields.length; i++) {
            try {
                // 允许这个值被修改
                declaredFields[i].setAccessible(true);
                Object prop = declaredFields[i].get(bean);
                if (null == prop) {
                    prop = initBasicValue(declaredFields[i].getType());
                    declaredFields[i].set(bean, prop);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @time 10:11
     * @params
     */
    public static String beanToAsciiSortString(Object bean) {
        /**
         * HashMap 默认根据Key采用ASCII排序  不启用下划线  忽略为空字段
         */
        Map<String, Object> map = BeanUtil.beanToMap(bean, new TreeMap<>(), false, true);

        StringBuilder sb = new StringBuilder();
        Object[] keys = map.keySet().toArray();
        for (int i = 0; i < keys.length; i++) {
            if (i == 0) {
                sb.append(keys[i] + "=" + map.get(keys[i]));
            }
            if (i + 1 != keys.length) {
                sb.append("&" + keys[i] + "=" + map.get(keys[i]));
            }
        }
        return sb.toString();
    }


    /**
     * @time 10:11
     * @params
     * @params
     */
    public static String beanToAsciiSortString1(Object bean) {
        /**
         * HashMap 默认根据Key采用ASCII排序  不启用下划线  忽略为空字段
         */
        Map<String, Object> map = BeanUtil.beanToMap(bean, new TreeMap<>(), false, true);

        StringBuilder sb = new StringBuilder();

        Set<Map.Entry<String, Object>> set = map.entrySet();
        /*Iterator it = set.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = String.valueOf(entry.getKey());
            Object v = entry.getValue();
            if (v != null && v != "") {
                if (!StringUtils.isEmpty(sb.toString())) {
                    sb.append("&");
                }
                sb.append(k).append("=").append(v);
            }
        }*/
        for (Map.Entry<String, Object> eset : set) {
            String k = String.valueOf(eset.getKey());
            Object v = eset.getValue();
            if (v != null && v != "") {
                if (!StringUtils.isEmpty(sb.toString())) {
                    sb.append("&");
                }
                sb.append(k).append("=").append(v);
            }
        }

        return sb.toString();
    }


    private static Object initBasicValue(Type type) {
        String typeName = type.getTypeName();
        if ("java.lang.Long".equals(typeName)) {
            return -1L;
        } else if ("java.lang.String".equals(typeName)) {
            return "";
        } else if ("java.lang.Integer".equals(typeName)) {
            return 1;
        } else if ("java.util.Date".equals(typeName)) {
            return new Date();
        }
        return null;
    }

}
