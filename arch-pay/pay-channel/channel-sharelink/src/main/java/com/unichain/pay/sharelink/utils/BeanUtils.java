/**
 * Project Name : pgw-core
 * File Name : BeanUtils.java
 * Package Name : com.i2f.ect.pgw.util
 * Date : 2015年6月2日上午10:33:13
 * Copyright (c) 2015, i2Finance Software All Rights Reserved
 */
package com.unichain.pay.sharelink.utils;

import java.lang.reflect.Field;
import java.util.*;

/**
 * ClassName : Bean工具类 <br/>
 * Date : 2015年6月2日 上午10:33:13 <br/>
 *
 * @author wanglong
 * @see
 * @since JDK 1.7
 */
public final class BeanUtils {

    public static final String METHOD_GET = "get";
    public static final String METHOD_SET = "set";
    public static final String EXCLUDE_FILED_NAME = "serialVersionUID";

    public static Map<String, Object> beanToMap(Object object) {
        if (null == object) {
            return null;
        }
        Map<String, Object> beanMap = new HashMap<String, Object>();
        Class<?> clazz = object.getClass();
        while (null != clazz) {
            clazz = wrapFieldToMap(clazz, object, beanMap);
        }
        return beanMap;
    }

    public static Field[] getAllFields(Object object) {
        Class clazz = object.getClass();
        List<Field> fieldList = new ArrayList<>();
        while (clazz != null) {
            fieldList.addAll(new ArrayList<>(Arrays.asList(clazz.getDeclaredFields())));
            clazz = clazz.getSuperclass();
        }
        Field[] fields = new Field[fieldList.size()];
        fieldList.toArray(fields);
        return fields;
    }

    private static Class<?> wrapFieldToMap(Class<?> clazz, Object object, Map<String, Object> beanMap) {
        Field[] fields = clazz.getDeclaredFields();
        if (null == fields) {
            return clazz.getSuperclass();
        }
        for (Field field : fields) {
            String fieldName = field.getName();
            if (EXCLUDE_FILED_NAME.equals(fieldName)) {
                continue;
            }
            Object obj = getFieldValueByName(clazz, object, fieldName);
            beanMap.put(fieldName, obj);
        }
        return clazz.getSuperclass();
    }

    public static Object getFieldValueByName(Class<?> clazz, Object obj, String fieldName) {
        if (null == clazz || null == obj || null == fieldName) {
            return null;
        }
        String methodName = null;
        if (fieldName.length() == 1) {
            methodName = METHOD_GET + fieldName.toUpperCase();
        } else {
            //获得getter方法，替换第一个变量的字母为大写（标准的驼峰写法）
            methodName = METHOD_GET + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
        }
        try {
            return clazz.getMethod(methodName).invoke(obj);
        } catch (Exception e) {
            return null;
        }
    }

    public static Map<String, Object> objectToMap(Object obj) throws Exception {
        if (obj == null) {
            return null;
        }

        Map<String, Object> map = new HashMap<String, Object>();

        Field[] declaredFields = obj.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            field.setAccessible(true);
            if (!EXCLUDE_FILED_NAME.equals(field.getName())) {
                map.put(field.getName().toUpperCase(), field.get(obj));
            }
        }
        return map;
    }

}
