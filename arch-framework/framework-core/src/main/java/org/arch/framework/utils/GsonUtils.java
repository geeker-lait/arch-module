package org.arch.framework.utils;

import com.google.gson.Gson;

/**
 * google Json工具类
 *
 * @date 2018-04-23 9:51
 */
public class GsonUtils {

    /**
     * 对象转JSON字符串
     * @param obj
     * @return String
     */
    public static String toJson(Object obj){
        if(obj == null){
            return null;
        }
        Gson gson = new Gson();
        return gson.toJson(obj);
    }


    /**
     * Json字符串转对象
     * @param json Json字符串
     * @param classOfT 对象类型
     * @param <T> 泛型，可自定义
     * @return 传入的对象类型
     */
    public static <T> T fromJson(String json, Class<T> classOfT){
        if(StringUtils.isEmpty(json)){
            return null;
        }
        Gson gson = new Gson();
        return gson.fromJson(json,classOfT);
    }

}

