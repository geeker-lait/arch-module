package org.arch.framework.beans.utils;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.TimeZone;

public final class JsonUtils {
    private static final Logger log = LoggerFactory.getLogger(JsonUtils.class);
    public static ObjectMapper mapper = new ObjectMapper();

    public JsonUtils() {
    }

    public static String toJson(Object object) {
        try {
            return mapper.writeValueAsString(object);
        } catch (Exception var2) {
            throw new RuntimeException(var2.getMessage(), var2);
        }
    }

    public static <T> T fromJson(String jsonString, Class<T> valueType) {
        Assert.notNull(valueType, "valueType is null ");
        if (jsonString != null && !"".equals(jsonString)) {
            try {
                return mapper.readValue(jsonString, valueType);
            } catch (Exception var3) {
                throw new RuntimeException(var3.getMessage(), var3);
            }
        } else {
            return null;
        }
    }

    public static <T> T fromJsonNoEx(String jsonString, Class<T> valueType) {
        Assert.notNull(valueType, "valueType is null ");
        if (jsonString != null && !"".equals(jsonString)) {
            try {
                return mapper.readValue(jsonString, valueType);
            } catch (Exception var3) {
                log.error(var3.getMessage(), var3);
                return null;
            }
        } else {
            return null;
        }
    }

    public static <T> T fromJson(InputStream is, Class<T> valueType) {
        Assert.notNull(valueType, "valueType is null ");
        Assert.notNull(is, "inputStream is null");

        try {
            return mapper.readValue(is, valueType);
        } catch (Exception var3) {
            throw new RuntimeException(var3.getMessage(), var3);
        }
    }

    public static <T extends Collection<S>, S> T fromJson(String jsonString, Class<T> collectionType, Class<S> elementType) {
        Assert.notNull(collectionType, "collectionType is null");
        Assert.notNull(elementType, "elementType is null");
        if (jsonString != null && !"".equals(jsonString)) {
            try {
                return mapper.readValue(jsonString, mapper.getTypeFactory().constructCollectionType(collectionType, elementType));
            } catch (Exception var4) {
                throw new RuntimeException(var4.getMessage(), var4);
            }
        } else {
            return null;
        }
    }

    public static <T> T fromJson(String jsonString, TypeReference<T> typeReference) {
        Assert.notNull(typeReference, "typeReference is null");
        if (jsonString != null && !"".equals(jsonString)) {
            try {
                return mapper.readValue(jsonString, typeReference);
            } catch (Exception var3) {
                throw new RuntimeException(var3.getMessage(), var3);
            }
        } else {
            return null;
        }
    }

    static {
        mapper.setSerializationInclusion(Include.NON_NULL);
        mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.enable(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL);
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        mapper.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
    }
}
