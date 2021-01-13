package org.arch.framework.automate.from.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.arch.framework.crud.CrudEntity;

import java.util.Date;

@TableName
@Data
public class FormField extends CrudEntity<FormField> {
    private String key;
    private String name;
    private String remark;
    private int tableId;



    //表单Id
    private Integer formId;
    //字段Id
    private Integer fieldId;
    //字段编码，自动生成，对应到数据库中的字段名
    private String fieldCode;
    //字段类型，对应到field_type表中的type_code
    private String fieldTyp;
    //字段名称
    private String fieldName;
    //字段描述
    private String fieldDescr;
    //字段排序
    private String fieldOrder;


}
