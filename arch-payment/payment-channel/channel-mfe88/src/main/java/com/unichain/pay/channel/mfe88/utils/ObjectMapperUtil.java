/**
 * @projectName: txpayservice
 * @fileName: ObjectMapperUtil.java
 * @Description: TODO(� � һ � 仰 � � � � � � � ļ � � � ʲô)
 * @author: HEXY
 * @CreateDate: Jul 21, 2014 3:32:44 PM
 * @version V1.0
 */
package com.unichain.pay.channel.mfe88.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletRequest;
import java.io.IOException;
import java.io.Reader;
import java.util.*;

/**
 * @ClassName: ObjectMapperUtil
 * @author: HEXY
 */
public class ObjectMapperUtil {
    private static final MyObjectMapper MAPPER = new MyObjectMapper();

    static {
        MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static void setFormatter(String formatter) {
        MAPPER.setFormatter(formatter);
    }

    public static ObjectMapper getObjectMapper() {
        return MAPPER;
    }

    public static <T> T readValue(ServletRequest httpRequest, TypeReference<T> c) {
        Enumeration<String> names = httpRequest.getParameterNames();
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            while (names.hasMoreElements()) {
                String param = names.nextElement();
                String value = httpRequest.getParameter(param);
                if (StringUtils.isNotEmpty(value))
                    map.put(param, value);
                // System.out.println(param+"---"+value);
            }
            return MAPPER.readValue(MAPPER.writeValueAsBytes(map), c);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> T readValue(ServletRequest httpRequest, Class<T> c) {
        Enumeration<String> names = httpRequest.getParameterNames();
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            while (names.hasMoreElements()) {
                String param = names.nextElement();
                String value = httpRequest.getParameter(param);
                if (StringUtils.isNotEmpty(value))
                    map.put(param, value);
                // System.out.println(param+"---"+value);
            }
            return MAPPER.readValue(MAPPER.writeValueAsBytes(map), c);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Map<String, String> readMap(ServletRequest httpRequest, Set<String> filter) {
        Enumeration<String> names = httpRequest.getParameterNames();
        Map<String, String> map = new HashMap<String, String>();
        try {
            while (names.hasMoreElements()) {
                String param = names.nextElement();
                String value = httpRequest.getParameter(param);
                if (filter.contains(param))
                    map.put(param, value);
            }
            return map;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> T readValue(Map<String, ?> map, Class<T> c) {
        try {
            return MAPPER.readValue(MAPPER.writeValueAsBytes(map), c);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> T readValue(String json, Class<T> c) {
        try {
            return MAPPER.readValue(json, c);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> T readValue(byte[] byteArray, Class<T> c) {
        try {
            return MAPPER.readValue(byteArray, c);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> T readValue(Reader r, Class<T> c) {
        try {
            return MAPPER.readValue(r, c);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> List<T> readList(String r, Class<T> c) {
        try {
            return MAPPER.readValue(r, MAPPER.getTypeFactory().constructParametricType(List.class, c));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> T readValue(String body, TypeReference<T> c) {
        try {
            return MAPPER.readValue(body, c);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> T readValue(byte[] byteArray, TypeReference<T> c) {
        try {
            return MAPPER.readValue(byteArray, c);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
