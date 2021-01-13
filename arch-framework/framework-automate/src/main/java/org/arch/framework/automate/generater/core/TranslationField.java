package org.arch.framework.automate.generater.core;

import java.util.HashMap;
import java.util.Map;

public final class TranslationField {
    //数据里的字段翻译
    private static final Map<String, String> certificatetypes;
    //表头字段翻译
    private static final Map<String, String> fieldMap;

    static {

        fieldMap = new HashMap<>();
        fieldMap.put("姓名", "name");
        fieldMap.put("证件类型", "certificatetype");
        fieldMap.put("证件号码", "certificateno");
        fieldMap.put("年龄", "age");

        fieldMap.put("表名/Table","table");
        fieldMap.put("列名/Column","column");
        fieldMap.put("数据类型/Type","type");
        fieldMap.put("长度/Length","length");
        //fieldMap.put("是否为空/Length","length");
        fieldMap.put("是否为空/Null(Y,N)","isnull");
        fieldMap.put("默认值/Default","defaultValue");
        fieldMap.put("是否主键/Primary(Y,N)","primaryKey");
        fieldMap.put("是否唯一/Unique(Y,N)","unique");
        fieldMap.put("外键/Forigen(可空)","forienKey");
        fieldMap.put("备注/Comment","comment");


        //此处有线程危险问题，所以这些map是不对外开放操作的
        certificatetypes = new HashMap<>();
        certificatetypes.put("身份证", "01");
        certificatetypes.put("驾驶证", "02");
    }

    public static Map<String, String> getCertificatetypes() {
        return certificatetypes;
    }

    public static Map<String, String> getfieldMap() {
        return fieldMap;
    }
}
