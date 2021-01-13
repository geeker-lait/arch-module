package org.arch.framework.automate.from.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.arch.framework.crud.CrudEntity;

@TableName
@Data
public class FormField extends CrudEntity<FormField> {
    private String key;
    private String name;
    private String remark;
    private int tableId;
}
