package org.arch.framework.automate.from.entity.bak;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.arch.framework.crud.CrudEntity;

@TableName
@Data
public class FormTable extends CrudEntity<FormTable> {
    private String name;
    private String key;
    private String category;
    private String remark;
}
