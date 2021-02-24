package org.arch.framework.automate.api.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author junboXiang
 * @version V1.0
 * 2021-02-18
 */
@Data
@Accessors(chain = true)
public class FormDefinitionJsonDto {

    /**
     * db 名称
     */
    private String name;

    private Table table;

    /**
     * table
     */
    @Data
    @Accessors(chain = true)
    public static class Table {

        /**
         * db 名称
         */
        private String name;

        /**
         *  字段列表
         */
        @JSONField(name = "field_list")
        private List<FieldList> fieldList;

        /**
         * 主键
         */
        private String pk;

        /**
         * 外键列表
         */
        @JSONField(name = "fk_list")
        private List<FkList> fkList;

        /**
         * 唯一索引
         */
        @JSONField(name = "unique_list")
        private List<UniqueList> uniqueList;

        /**
         * 普通索引
         */
        @JSONField(name = "index_list")
        private List<IndexList> indexList;

        /**
         * 表的描述
         */
        private String comment;
    }

    @Data
    @Accessors(chain = true)
    public static class FieldList{
        private int id;

        private String name;

        private String jdbcType;

        private String length;

        @JSONField(name = "not_null")
        private Boolean notNull;

        private String comment;

    }

    @Data
    @Accessors(chain = true)
    public static class FkList {
        private String name;

        private String references;

        private String comment;


    }

    @Data
    @Accessors(chain = true)
    public static class UniqueList {
        private String name;

        private List<String> filed;

        private String comment;

    }

    @Data
    @Accessors(chain = true)
    public static class IndexList {
        private String name;

        private List<String> filed;

        private String comment;

    }

}
