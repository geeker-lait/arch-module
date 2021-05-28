package org.arch.form.crud.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.arch.framework.automate.api.Constants;
import org.arch.form.api.dto.DefinitionTableDto;

import java.util.List;
import java.util.Map;

/**
 * @author junboXiang
 * @version V1.0
 * 2021-02-18
 */
@Mapper
@DS(Constants.DATASOURCE_MASTER_DDL)
public interface DDLMapper {

    /**
     * 判断数据库是否存在
     *
     * @param database
     * @return
     */
    int existDatabase(@Param("database") String database);

    /**
     * 创建数据库
     *
     * @param database
     * @return
     */
    int createDatabase(@Param("database") String database);

    /**
     * 删除指定数据库
     *
     * @param database
     * @return
     */
    int dropDatabase(@Param("database") String database);


    /**
     * 判断指定库下表是否存在
     *
     * @param database
     * @param tableName
     * @return
     */
    int existTable(@Param("database") String database, @Param("tableName") String tableName);


    /**
     * 删除表
     *
     * @param database
     * @param tableName
     * @return
     */
    int dropTable(@Param("database") String database, @Param("tableName") String tableName);

    /**
     * 创建 table
     *
     * @param record
     * @return
     */
    int createTable(@Param("record") DefinitionTableDto record);

    /**
     * 获取指定库下所有的表
     *
     * @param database
     * @return
     */
    List<Map<String, String>> getDatabaseTables(@Param("database") String database);

    /**
     * 获取指定库下的指定表的所有字段信息
     *
     * @param database
     * @param tableName
     * @return
     */
    List<Map<String, String>> getTableDetail(@Param("database") String database, @Param("tableName") String tableName);


}
