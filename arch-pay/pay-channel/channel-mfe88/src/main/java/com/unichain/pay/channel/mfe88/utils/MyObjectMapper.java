/**
 * @projectName: jpademo
 * @fileName: JsonDateObjectMapper.java
 * @Description:
 * @author: HEXY
 * @CreateDate: Jan 19, 2015 3:43:46 PM
 * @version V1.0
 */
package com.unichain.pay.channel.mfe88.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.*;

import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @ClassName: JsonDateObjectMapper
 */
public class MyObjectMapper extends ObjectMapper {
    /**
     * @Fields serialVersionUID :
     */
    private static final long serialVersionUID = -1576324702054837966L;
    private String formatter = "yyyy-MM-dd HH:mm:ss";// 默认格式

    public MyObjectMapper() {
        configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false);
        configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);// 取消默认TIMESTAMPS
        configure(SerializationFeature.WRITE_ENUMS_USING_TO_STRING, true); // 枚举取toString
        disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        // 空值处理为空串
        SerializerProvider sp = this.getSerializerProvider();
        sp.setNullValueSerializer(new JsonSerializer<Object>() {
            @Override
            public void serialize(Object value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
                jgen.writeString("");
            }
        });

        DateFormat df = new SimpleDateFormat(this.formatter);
        setDateFormat(df);
    }

    public static void main(String[] args) throws IOException {
        String aString = "{\"a\": \"2016-03-16 21:03:24\"}";
        MyObjectMapper objectMapper = new MyObjectMapper();
        A m = objectMapper.readValue(aString, A.class);
        System.out.println();
    }

    public void setFormatter(String formatter) {
        this.formatter = formatter;
        DateFormat df = new SimpleDateFormat(this.formatter);
        setDateFormat(df);
    }

    class A implements Serializable {
        private Date a;

        public Date getA() {
            return a;
        }

        public void setA(Date a) {
            this.a = a;
        }
    }
}
