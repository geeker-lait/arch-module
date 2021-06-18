package org.arch.form.core.utils;

import com.google.common.base.CaseFormat;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.arch.form.api.dto.DefinitionTableDto;
import org.arch.form.api.dto.FormDefinitionJsonDto;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author junboXiang
 * @version V1.0
 * 2021-02-20
 */
@Slf4j
public class DefinitionTableUtil {


    /**
     * 根据sql数据类型获取Java数据类型
     *
     * @param columnType
     * @return
     */
    /*public static Class getFieldType(String columnType) {
        Class aClass = JdbcTypeUtils.toJavaTyp(columnType);
        if (aClass == null) {
            return sqlFieldTypeMapping.get(columnType.toUpperCase());
        }
        return aClass;
    }*/

    /**
     * 对 table json 数据实体进行校验
     *
     * @param dto
     */
    public static void check(FormDefinitionJsonDto dto) {

    }

    /**
     * 构建 create参数
     */
    public static DefinitionTableDto buildCreateParams(FormDefinitionJsonDto dto) {
        if (dto == null) {
            return null;
        }
        DefinitionTableDto definitionTableDto = new DefinitionTableDto();
        FormDefinitionJsonDto.Table table = dto.getTable();
        if (CollectionUtils.isEmpty(table.getFieldList())) {
            return null;
        }
        definitionTableDto.setDatabaseName(dto.getName())
                .setTableName(table.getName())
                .setPk(table.getPk())
                .setComment(table.getComment());
        definitionTableDto.setFieldList(table.getFieldList().stream().map(fieldList -> {
            DefinitionTableDto.Field field = new DefinitionTableDto.Field();
            return field.setJdbcType(fieldList.getJdbcType())
                    .setLength(fieldList.getLength())
                    .setNotNull(fieldList.getNotNull())
                    .setName(camelToUnderscore(fieldList.getName()))
                    .setComment(fieldList.getComment());
        }).collect(Collectors.toList()));
        definitionTableDto.setIndexList(CollectionUtils.isEmpty(table.getIndexList()) ? null :
                table.getIndexList().stream().filter(indexList -> CollectionUtils.isNotEmpty(indexList.getFiled())).map(indexList -> {
                    DefinitionTableDto.Index index = new DefinitionTableDto.Index();
                    return index.setComment(indexList.getComment())
                            .setFieldList(indexList.getFiled().stream().map(DefinitionTableUtil::camelToUnderscore).collect(Collectors.toList()))
                            .setName(StringUtils.isNotBlank(indexList.getName()) ? indexList.getName() : "idx_" + Joiner.on("_").join(index.getFieldList()));
                }).collect(Collectors.toList()));
        definitionTableDto.setUniqueList(CollectionUtils.isEmpty(table.getUniqueList()) ? null :
                table.getUniqueList().stream().filter(uniqueList -> CollectionUtils.isNotEmpty(uniqueList.getFiled())).map(uniqueList -> {
                    DefinitionTableDto.Unique unique = new DefinitionTableDto.Unique();
                    return unique.setComment(uniqueList.getComment())
                            .setFieldList(uniqueList.getFiled().stream().map(DefinitionTableUtil::camelToUnderscore).collect(Collectors.toList()))
                            .setName(StringUtils.isNotBlank(uniqueList.getName()) ? uniqueList.getName() : "unique_" + Joiner.on("_").join(unique.getFieldList()));
                }).collect(Collectors.toList()));
        return definitionTableDto;
    }

    /**
     * 根据最后两个版本比较 字段差异
     *
     * @param latestVersionDto 最新的版本数据
     * @param lastVersionDto   上个版本数据
     * @return list   0 新增的字段 1 需要修改的字段     2 需要删除的字段
     */
    public static List<List<FormDefinitionJsonDto.FieldList>> findDifferenceFiled(FormDefinitionJsonDto latestVersionDto, FormDefinitionJsonDto lastVersionDto) {
        List<FormDefinitionJsonDto.FieldList> insertList = Lists.newArrayList();
        List<FormDefinitionJsonDto.FieldList> updateList = Lists.newArrayList();
        List<FormDefinitionJsonDto.FieldList> deleteList = Lists.newArrayList();
        if (latestVersionDto == null || lastVersionDto == null) {
            return Lists.newArrayList(insertList, updateList, deleteList);
        }

        try {
            // 最新的数据
            List<FormDefinitionJsonDto.FieldList> latestVersionFieldList = latestVersionDto.getTable().getFieldList();
            // 上个版本数据
            List<FormDefinitionJsonDto.FieldList> lastVersionFieldList = lastVersionDto.getTable().getFieldList();
            if (CollectionUtils.isEmpty(latestVersionFieldList) && CollectionUtils.isEmpty(lastVersionFieldList)) {
                return Lists.newArrayList(insertList, updateList, deleteList);
            }
            // 新版本数据为空，上个版本为不空
            if (CollectionUtils.isEmpty(latestVersionFieldList) && CollectionUtils.isNotEmpty(lastVersionFieldList)) {
                deleteList.addAll(lastVersionFieldList);
                return Lists.newArrayList(insertList, updateList, deleteList);
            }
            if (CollectionUtils.isNotEmpty(latestVersionFieldList) && CollectionUtils.isEmpty(lastVersionFieldList)) {
                insertList.addAll(latestVersionFieldList);
                return Lists.newArrayList(insertList, updateList, deleteList);
            }
            Map<String, FormDefinitionJsonDto.FieldList> latestMapFieldList = latestVersionFieldList.stream().collect(Collectors.toMap(FormDefinitionJsonDto.FieldList::getName, field -> field));
            Map<String, FormDefinitionJsonDto.FieldList> lastMapFieldList = lastVersionFieldList.stream().collect(Collectors.toMap(FormDefinitionJsonDto.FieldList::getName, field -> field));

            // 分离 insert  update
            latestVersionFieldList.forEach(field -> {
                String name = field.getName();
                if (lastMapFieldList.containsKey(name)) {
                    updateList.add(field);
                } else {
                    insertList.add(field);
                }
            });
            // 分离inxert 数据
            deleteList.addAll(lastVersionFieldList.stream().filter(field -> !latestMapFieldList.containsKey(field.getName())).collect(Collectors.toList()));
        } catch (Exception e) {
            log.info("Compare the failure: latestVersionDto:{} lastVersionDto:{}", latestVersionDto, lastVersionDto, e);
        }
        return Lists.newArrayList(insertList, updateList, deleteList);
    }

    /**
     * 将str驼峰转下划线       userName -> user_name
     *
     * @param str
     * @return
     */
    public static String camelToUnderscore(String str) {
        if (StringUtils.isEmpty(str)) {
            return str;
        }
        return CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, str);
    }

    /**
     * 将str下划线转驼峰       user_name -> userName
     *
     * @param str
     * @return
     */
    public static String lowerUnderscoreToLowerCamel(String str) {
        if (StringUtils.isEmpty(str) || str.indexOf("_") < 0) {
            return str;
        }
        return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, str);
    }


}
