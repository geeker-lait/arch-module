package org.arch.framework.automate.generater.parser;

import lombok.extern.slf4j.Slf4j;
import org.arch.framework.automate.generater.metadata.AnnotationInfo;
import org.arch.framework.automate.generater.metadata.EntityInfo;
import org.arch.framework.automate.generater.metadata.FieldInfo;
import org.arch.framework.automate.generater.metadata.IdInfo;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

/**
 * 默认的实体解析器
 */
@Slf4j
public abstract class AbstractEntityParser implements EntityParseable {

    @Override
    public final EntityInfo parse(Class<?> clazz) {
        EntityInfo entityInfo = parseEntity(clazz);
        entityInfo.setFields(parseField(clazz));
        entityInfo.setId(parseId(parseField(clazz)));
        return entityInfo;
    }



    @Override
    public List<FieldInfo> parseField(Class<?> clazz) {
        List<FieldInfo> fields = new ArrayList<>();
        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field f : declaredFields) {
            // 静态变量不加入生成的传输类中
            if (Modifier.isStatic(f.getModifiers())) {
                log.info("字段={} 为静态变量字段，忽略作为传输类的字段", f.getName());
                continue;
            }
            fields.add(getFieldInfo(f));
        }
        return fields;
    }

    /**
     * 解析字段信息
     *
     * @param f
     * @return
     */
    public FieldInfo getFieldInfo(Field f) {
        FieldInfo fieldInfo = new FieldInfo();

        fieldInfo.setClassName(f.getType().getSimpleName());
        fieldInfo.setPackageName(f.getType().getTypeName());

        fieldInfo.setName(f.getName());

        // 字段注解
        Annotation[] fieldAnnotations = f.getAnnotations();
        if (fieldAnnotations.length > 0) {
            List<AnnotationInfo> annotationInfos = new ArrayList<>();

            for (Annotation annotation : fieldAnnotations) {
                AnnotationInfo annotationInfo = new AnnotationInfo();
                annotationInfo.setClassName(annotation.annotationType().getSimpleName());
                annotationInfo.setPackageName(annotation.annotationType().getPackage().getName());
            }
            fieldInfo.setAnnotations(annotationInfos);
        }
        return fieldInfo;
    }

    /**
     * 解析实体信息
     *
     * @param clazz 指定实体类
     * @return 实体信息
     */
    public abstract EntityInfo parseEntity(Class<?> clazz);

    /**
     * 解析主键信息
     *
     * @param clazz 指定实体类
     * @return 主键信息
     */
    public abstract IdInfo parseId(Class<?> clazz);


    /**
     * 解析主键信息
     *
     * @param parseField 指定实体类字段
     * @return 主键信息
     */
    public abstract IdInfo parseId(List<FieldInfo> parseField);
}
