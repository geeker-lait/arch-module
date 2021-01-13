package org.arch.framework.automate.from.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.arch.framework.crud.CrudEntity;

@TableName
@Data
public class FormDefinition extends CrudEntity<FormDefinition> {
    private String category;
    private int fieldSourceId;
    private int layoutSourceId;
    private String key;
    private String name;
    private String relationTable;
    private int version;
}
