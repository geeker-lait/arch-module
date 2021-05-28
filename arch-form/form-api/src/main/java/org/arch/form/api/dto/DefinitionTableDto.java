package org.arch.form.api.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author junboXiang
 * @version V1.0
 * 2021-02-20
 */
@Data
@Accessors(chain = true)
public class DefinitionTableDto {

    /**
     * 数据库名称
     */
    private String databaseName;

    /**
     * 表名称
     */
    private String tableName;

    /**
     * 表的描述
     */
    private String comment;

    /**
     * 主键
     */
    private String pk;

    /**
     *  字段列表
     */
    private List<Field> fieldList;

//    /**
//     * alter 表时使用 需要删除的字段
//     */
//    private List<Field> dropFieldList;

    /**
     * 外键列表
     */
    private List<Fk> fkList;

//    /**
//     * alter 时使用 需要删除的外键
//     */
//    private List<Fk> dropFkList;

    /**
     * 唯一索引
     */
    private List<Unique> uniqueList;

//    /**
//     * alter 时使用 需要删除的唯一索引
//     */
//    private List<Unique> dropUniqueList;

    /**
     * 普通索引
     */
    private List<Index> indexList;

//    /**
//     * alter 时使用 需要删除的普通索引
//     */
//    private List<Index> dropIndexList;


    @Data
    @Accessors(chain = true)
    public static class Field{
        private int id;

        private String name;

        private String jdbcType;

        private String length;

        private Boolean notNull;

        private String comment;

    }

    @Data
    @Accessors(chain = true)
    public static class Fk {
        private String name;

        private String references;

        private String comment;


    }

    @Data
    @Accessors(chain = true)
    public static class Unique {
        private String name;

        private List<String> fieldList;

        private String comment;

    }

    @Data
    @Accessors(chain = true)
    public static class Index {
        private String name;

        private List<String> fieldList;

        private String comment;

    }


}
