package org.arch.framework.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

/**
 * @author lait.zhang@gmail.com
 * @description: 将一个层级类对象(领域驱动model对象)转换为平铺类对象(view)
 * @weixin PN15855012581
 * @date 12/31/2020 11:00 AM
 */
public class ReflectUtils {

    public static <T> T convert(Object src, Class<T> tClass) {
        Constructor<T> cons = null;
        try {
            cons = tClass.getDeclaredConstructor(null);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        cons.setAccessible(true);
        T result = null;
        try {
            result = cons.newInstance(null);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        convert(src, result);
        return result;
    }

    private static void getSrcALLFieldMap(Object obj, Map<String, Object> collectMap) {
        Class srcClazz = obj.getClass();
        Field[] srcFields = srcClazz.getDeclaredFields();
        Stream.of(srcFields).forEach(field -> {
            field.setAccessible(true);
            try {
                if (field.getType().toString().contains("aacoin.account") && !field.getType().isEnum()) {
                    Object subObj = field.get(obj);
                    if (subObj != null)
                        getSrcALLFieldMap(subObj, collectMap);
                } else {
                    collectMap.put(field.getName(), field.get(obj));
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });
    }

    private static void setTargetAllField(Object obj, Map<String, Object> srcMap) {
        Class srcClazz = obj.getClass();
        Field[] srcFields = srcClazz.getDeclaredFields();
        Stream.of(srcFields).forEach(field -> {
            field.setAccessible(true);
            try {
                if (field.getType().toString().contains("aacoin.trade.otc") && !field.getType().isEnum()) {
                    Object subObj = field.get(obj);
                    if (subObj == null) {
                        Constructor cons = field.getType().getDeclaredConstructor(null);
                        cons.setAccessible(true);
                        subObj = cons.newInstance(null);
                        field.set(obj, subObj);
                    }
                    setTargetAllField(subObj, srcMap);
                } else {
                    //collectMap.put(field,obj);
                    Object currentField = srcMap.get(field.getName());
                    if (currentField != null && field.getType() == currentField.getClass()) {
                        field.set(obj, currentField);
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        });
    }



    private static <T> T convert(Object src, T result) {
        Map<String, Object> srcMap = new HashMap();
        getSrcALLFieldMap(src, srcMap);

        setTargetAllField(result, srcMap);
        return result;
    }
}
