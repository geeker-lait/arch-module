package com.unichain.pay.channel.mfe88.utils;

import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by bibei on 2017/3/1.
 */
public class JsonUtils extends JSON {
    public static String beanToJson(Object object) {
        return JSON.toJSONString(object);
    }

    public static String beanToJsonWhithDataFormart(Object object, String formart) {
        return JSON.toJSONStringWithDateFormat(object, formart);
    }

    /**
     * 将json结果集转化为对象
     *
     * @param jsonData json数据
     * @param clazz    对象中的object类型
     * @return
     */
    public static <T> T jsonToBean(String jsonData, Class<T> clazz) {
        try {
            T t = JSON.parseObject(jsonData, clazz);
            return t;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将json数据转换成pojo对象list
     * <p>Title: jsonToList</p>
     * <p>Description: </p>
     *
     * @param jsonData
     * @return
     */
    public static <T> List<T> jsonToList(String jsonData, Class<T> clazz) {
        try {
            List<T> list = JSON.parseArray(jsonData, clazz);
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void main(String[] args) {
        Map map = new HashMap();
        map.put("ss", 1);
        map.put("adsf", null);
        String js = beanToJson(map);
        System.out.println(js);
    }

}
