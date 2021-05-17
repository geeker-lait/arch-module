package org.arch.alarm.api.annotation;

import com.google.common.base.CaseFormat;
import org.arch.alarm.api.pojo.biz.AlarmRegData;

import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author lait.zhang@gmail.com
 * @description: 对象属性与注解映射及其设置映射value
 * @weixin PN15855012581
 * @date 4/29/2021 2:55 PM
 */
public class RegPropertyUtils {

    /**
     * 缓存Class字段信息
     */
    private static Map<Class<?>, Map<String, String>> CACHE_CLASS_INFO = new ConcurrentHashMap(32);


    public static Map<String, Object> toMap(AlarmRegData alarmRegData) {
        Class cls = alarmRegData.getClass();
        //Map<String, String> map = getBeanMetaByRowField(cls);
        Map<String, Object> toMap = new HashMap<>();
        while (cls != Object.class) {
            Field[] fields = cls.getDeclaredFields();
            List<Field> list = new ArrayList<>();
            list.addAll(Arrays.asList(fields));
            for (int i = 0; i < fields.length; i++) {
                Field field = fields[i];
                RegProperty fieldAnnotation = field.getDeclaredAnnotation(RegProperty.class);
                if (fieldAnnotation == null) {
                    continue;
                }
                String key = fieldAnnotation.value();
                field.setAccessible(true);
                if (key == null) {
                    // todo 转驼峰
                    key = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, field.getName());
                }
                try {
                    toMap.put(key, field.get(alarmRegData));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            // 遍历所有父类字节码对象
            cls = cls.getSuperclass();
        }
        return toMap;
    }

    /**
     * 获取指定类的映射信息
     *
     * @param clazz
     * @param <T>
     * @return 返回映射信息
     */
    private static <T> Map<String, String> getBeanMetaByRowField(Class<T> clazz) {
        Map<String, String> map = CACHE_CLASS_INFO.get(clazz);
        if (map != null && !map.isEmpty()) {
            return CACHE_CLASS_INFO.get(clazz);
        }
        Map<String, String> headerData = new HashMap<>();
        Class<?> cls = clazz;
        //遍历所有父类字节码对象
        while (cls != Object.class) {
            Field[] fields = cls.getDeclaredFields();
            List<Field> list = new ArrayList<>();
            list.addAll(Arrays.asList(fields));
            for (int i = 0; i < fields.length; i++) {
                Field field = fields[i];
                RegProperty fieldAnnotation = field.getDeclaredAnnotation(RegProperty.class);
                if (fieldAnnotation != null) {
                    field.setAccessible(true);
                    String name = field.getName();
                    headerData.put(fieldAnnotation.name(), name);
                } else {
                    String name = field.getName();
                    // todo 转驼峰
                    headerData.put(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, name), name);
                }
            }
            // 遍历所有父类字节码对象
            cls = cls.getSuperclass();
        }
        CACHE_CLASS_INFO.put(clazz, headerData);
        return headerData;
    }

    /**
     * 将map数据映射到指定对象中
     *
     * @param mapData
     * @param clazz
     * @param <T>
     * @return
     */
    private static <T> T mapToBean(Map<String, Object> mapData, Class<T> clazz) {
        T instance = null;
        try {
            instance = clazz.newInstance();
            Map<String, String> meta = getBeanMetaByRowField(clazz);
            for (Map.Entry<String, Object> entry : mapData.entrySet()) {
                String fieldName = entry.getKey();
                Object dataValue = entry.getValue();
                //存在属性则设置
                if (meta.containsKey(fieldName)) {
                    setValue(instance, meta.get(fieldName), dataValue);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return instance;
    }

    /**
     * @param obj
     * @param fieldName
     * @param value
     * @return
     * @throws
     * @Description 设置 属性值
     */
    private static void setValue(Object obj, String fieldName, Object value) {
        try {
            Field field = obj.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(obj, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
