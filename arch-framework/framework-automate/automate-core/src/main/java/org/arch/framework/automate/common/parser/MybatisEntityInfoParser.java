package org.arch.framework.automate.common.parser;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import org.arch.framework.automate.common.metadata.AnnotationInfo;
import org.arch.framework.automate.common.metadata.EntityInfo;
import org.arch.framework.automate.common.metadata.FieldInfo;
import org.arch.framework.automate.common.metadata.PkFiledInfo;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.List;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date 12/8/2020 6:19 PM
 */

public class MybatisEntityInfoParser extends AbstractEntityParser {

    /**
     * 主键注解
     * mp&tkmybaits
     */
    private static final List<Class<? extends Annotation>> ID_CLASS_LIST = Arrays.asList(TableId.class);

    @Override
    public EntityInfo parseEntity(Class<?> clazz) {
        EntityInfo entityInfo = new EntityInfo();
        entityInfo.setClassName(clazz.getSimpleName());

        String packageName = clazz.getPackage().getName();

        entityInfo.setPackageName(packageName);

        TableName tableAnnotation = clazz.getAnnotation(TableName.class);
        if (tableAnnotation != null) {
            entityInfo.setTableName(tableAnnotation.value());
        }
        return entityInfo;
    }


    @Override
    public PkFiledInfo parseId(Class<?> clazz) {

        return null;
    }

    @Override
    public PkFiledInfo parseId(List<FieldInfo> parseField) {
        for (FieldInfo fieldInfo : parseField) {
            AnnotationInfo annotationInfo = fieldInfo.getAnnotations().stream()
                    .filter(ai -> ai.getClassName().equals(TableId.class.getName())).findFirst().get();
            if (annotationInfo != null) {
                PkFiledInfo pkFiledInfo = new PkFiledInfo();
                pkFiledInfo.setModuleName(annotationInfo.getModuleName());
                pkFiledInfo.setClassName(annotationInfo.getClassName());
                pkFiledInfo.setPackageName(annotationInfo.getPackageName());
                return pkFiledInfo;
            }
        }
        return null;
    }

}
