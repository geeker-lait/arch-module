package org.arch.framework.automate.from.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.arch.framework.crud.CrudEntity;

@TableName
@Data
public class FormInstance extends CrudEntity {

    //表单ID
    private Integer formId;
    //表单名称
    private String formName;
    //表单对应的表ID
    private Integer formTableId;
    //表单对应数据库中生成的table name
    private String formTableName;
    //表单描述
    private String formDescr;
}
