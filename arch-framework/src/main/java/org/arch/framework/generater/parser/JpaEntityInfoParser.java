package org.arch.framework.generater.parser;

import org.arch.framework.generater.metadata.EntityInfo;
import org.arch.framework.generater.metadata.FieldInfo;
import org.arch.framework.generater.metadata.IdInfo;
import org.arch.framework.generater.utils.ReflectUtils;

import javax.persistence.IdClass;
import javax.persistence.Table;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date 12/8/2020 6:18 PM
 */
public class JpaEntityInfoParser extends AbstractEntityParser {

    /**
     * 主键注解
     */
    private static final List<Class<? extends Annotation>> ID_CLASS_LIST = Arrays.asList(javax.persistence.Id.class);

    @Override
    public EntityInfo parseEntity(Class<?> clazz) {
        EntityInfo entityInfo = new EntityInfo();
        entityInfo.setClassName(clazz.getSimpleName());

        String packageName = clazz.getPackage().getName();

        entityInfo.setPackageName(packageName);

        Table tableAnnotation = clazz.getAnnotation(javax.persistence.Table.class);
        if (tableAnnotation != null) {
            entityInfo.setTableName(tableAnnotation.name());
        }
        return entityInfo;
    }

    @Override
    public IdInfo parseId(Class<?> clazz) {
        // 尝试从类注解获取主键信息
        IdClass idClassAnnotation = clazz.getAnnotation(javax.persistence.IdClass.class);
        if (idClassAnnotation != null) {
            IdInfo idInfo = new IdInfo();
            idInfo.setClassName(idClassAnnotation.value().getSimpleName());
            idInfo.setPackageName(idClassAnnotation.value().getTypeName());
            return idInfo;
        }
        // 尝试获取第一个带主键注解的字段
        for (Class<? extends Annotation> idClass : ID_CLASS_LIST) {
            Optional<Field> fieldByAnnotation = ReflectUtils.getFieldByAnnotation(clazz, idClass);
            if (fieldByAnnotation.isPresent()) {
                Field field = fieldByAnnotation.get();
                IdInfo idInfo = new IdInfo();
                idInfo.setClassName(field.getType().getSimpleName());
                idInfo.setPackageName(field.getType().getTypeName());
                return idInfo;
            }
        }
        // try from super class
        Class<?> maybeExists = clazz.getSuperclass();
        if (!Object.class.equals(maybeExists)) {
            return parseId(maybeExists);
        }
        return null;
    }

    @Override
    public IdInfo parseId(List<FieldInfo> parseField) {
        return null;
    }

    @Override
    public List<FieldInfo> parseField(Class<?> clazz) {
        return null;
    }
}
