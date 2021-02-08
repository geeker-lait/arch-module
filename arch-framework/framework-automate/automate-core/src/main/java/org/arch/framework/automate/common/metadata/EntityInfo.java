package org.arch.framework.automate.common.metadata;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 实体信息
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class EntityInfo extends BaseClassInfo {

    /**
     * 表名
     */
    private String tableName;
    /**
     * 注释
     */
    private String comment;
    /**
     * 主键类名
     */
    private PkFiledInfo id;
    /**
     * 所有< 属性名, 类 >
     */
    private List<FieldInfo> fields;
    /**
     * 外键
     */
    private List<FkFiledInfo> fkFiledInfos;
    /**
     * 唯一健
     */
    private List<UniqueFiledInfo> uniqueFiledInfos;




}
