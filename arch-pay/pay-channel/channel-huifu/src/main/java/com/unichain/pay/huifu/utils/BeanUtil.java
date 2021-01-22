package com.unichain.pay.huifu.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.unichain.pay.huifu.exception.PayErrException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class BeanUtil {
    private static final Logger logger = LoggerFactory.getLogger(BeanUtil.class);

    /**
     * 转换bean
     *
     * @param obj
     * @return
     */
    public static Map<String, Object> Bean2Map(Object obj) {
        if (obj == null) {
            return null;
        }
        try {
            JSONObject jsonObject = (JSONObject) JSON.toJSON(obj);
            return jsonObject;
        } catch (Exception e) {
            logger.error("transMap2Bean2 Error " + e);
            throw new PayErrException("transMap2Bean Error ", e);
        }

    }

    /**
     * 转换map
     *
     * @param <T>
     * @param map
     * @param clazz
     * @return
     */
    public static <T> T Map2Bean(Map<String, Object> map, Class<T> clazz) {
        if (map == null) {
            return null;
        }

        JSONObject jsonObject = new JSONObject(map);
        return JSON.toJavaObject(jsonObject, clazz);
    }

    /**
     * javaBean转Map
     *
     * @param bean
     * @return
     * @throws Exception
     */
    public static Map<String, Object> transforObjectToMap(Object bean) {
        if (bean == null) {
            return null;
        }

        Map<String, Object> map = new HashMap<String, Object>();
        Field[] declaredFields = bean.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            field.setAccessible(true);
            try {
                //不需要put到map中的对象
                Object val = field.get(bean);
                String key = field.getName();
                if (null != val) {
                    map.put(key, val);
                }
            } catch (IllegalAccessException ex) {
                logger.error(String.format("transforObjectToMap Error"), ex);
            }
        }
        return map;
    }

    /**
     * Map对象转javaBean
     *
     * @param map
     * @param obj
     */
    public static Object transMapToBean(Map<String, String> map, Object obj) {
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();
                if (map.containsKey(key)) {
                    Object value = map.get(key);
                    // 得到property对应的setter方法
                    Method setter = property.getWriteMethod();
                    try {
                        Method getter = property.getReadMethod();
                        Type returnType = getter.getGenericReturnType();// 返回类型
                        if ("int".equals(returnType.toString())) {
                            value = Integer.parseInt(value.toString());
                        } else if ("java.lang.String".equals(returnType.toString())
                                || ("class java.lang.String".equals(returnType.toString()))) {
                            if (value == null) {
                                value = "";
                            } else {
                                value = value.toString();
                            }

                        }
                        if ("float".equals(returnType.toString())) {
                            value = Float.parseFloat(value.toString());
                        }
                        setter.invoke(obj, value);
                    } catch (Exception e) {
                        logger.error(String.format("transMapToBean Error key:%s value:%s", key, value), e);
                    }
                }
            }
        } catch (Exception e) {
            logger.error(String.format("transMapToBean Error"), e);
        }
        return obj;
    }

    /**
     * 判断字符串是否是json
     *
     * @param content
     * @return
     */
    public static boolean isJson(String content) {
        try {
            JSONObject jsonStr = JSONObject.parseObject(content);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static String ascByMapKey(Map<String, Object> params) {
        if (null == params || params.size() == 0) {
            return "";
        }
        if (params instanceof HashMap) {
            params = new TreeMap<>(params);
        }
        StringBuilder s1 = new StringBuilder();
        for (String key : params.keySet()) {
            if (null != params.get(key)) {
                s1.append(key).append("=").append(params.get(key)).append("&");
            }
        }
        return s1.deleteCharAt(s1.length() - 1).toString();
    }
}
