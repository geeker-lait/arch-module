package org.arch.framework.automate.from.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.arch.framework.crud.CrudEntity;

@TableName
@Data
public class FormInstance extends CrudEntity {
    private int formDefinitionId;
    private String formDefinitionName;
    private String relationTable;
    private int tableRelationId;
}
