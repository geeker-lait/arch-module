package org.arch.framework.automate.from.entity.bak;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.arch.framework.crud.CrudEntity;

@TableName
@Data
public class FormLayout extends CrudEntity<FormLayout> {
    private int editorSourceId;
    private String key;
    private String name;
    private int tableId;
    private String remark;

}
