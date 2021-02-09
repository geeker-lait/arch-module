package org.arch.framework.automate.from.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.arch.framework.automate.from.entity.FormDefinition;
import org.arch.framework.crud.CrudMapper;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 表单定义(FormDefinition) 表数据库 Mapper 层
 *
 * @author lait
 * @date 2021-02-08 13:25:12
 * @since 1.0.0
 */
@Mapper
public interface FormDefinitionMapper extends CrudMapper<FormDefinition> {
    //删除该表的备份表
    int dropTable(@Param("tableName") String tableName);

    //将数据取出,每条数据的列需要有序.
    List<LinkedHashMap> queryAllData(@Param("tableName") String tableName);

    //将原表改为备份表名
    void renameTableToBak(@Param("tableName") String tableName, @Param("tableNameBak") String tableNameBak);

    /**
     * @return java.util.Map:{
     * constraintName:primary_208
     * column_name:key
     * }
     * @Param [bakName]
     * @Description 获取该表的主键
     */
    String getTableKey(String tableNameBak);

    String getTableComments(String tableName);

    /**
     * @return [
     * <p>
     * //list需要有序(每个字段之间的顺序)
     * {
     * //map可以无序(字段的属性)
     * *              column_name,
     * *              data_type,
     * *              data_length,
     * *              data_precision
     * *              data_scale,
     * *              nullable,
     * *              comments,
     * },{
     * <p>
     * }
     * ]
     * @Author SunYH
     * @Date 2020/12/31 14:03
     * @Param [bakName]
     * @Description 获取表中字段的信息
     */
    List<Map> getTableStruct(String tableName);

    /**
     * @return void
     * @Param [map]
     * @Description map:{
     * tableName,
     * tableComments,
     * CONSTRAINT_NAME,
     * COLUMN_NAME,
     * columns:[//有序集合,ArrayList
     * {
     * //第一列字段的属性
     * COLUMN_NAME,
     * DATA_TYPE,
     * CHAR_LENGTH,
     * DATA_PRECISION
     * DATA_SCALE,
     * NULLABLE,
     * COMMENTS
     * },{
     * //第一列字段的属性
     * }
     * ]
     * }
     */
    void createNewTable(@Param("map") Map map);


    int insertBatch(@Param("tableName") String tableName, @Param("data") List<LinkedHashMap> data);

    int updateTableComment(@Param("tableName") String tableName, @Param("tableComments") String tableComments);

    int updateColumnComments(@Param("tableName") String tableName, @Param("columnName") String columnName, @Param("comments") String comments);
}
