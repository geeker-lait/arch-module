package org.arch.application.form.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
public class Field {

    //字段Id
    private Integer fieldId;
    //表单Id
    private Integer formId;
    //字段编码，自动生成，对应到数据库中的字段名
    private String fieldCode;
    //字段类型，对应到upload_field_type表中的type_code
    private String fieldType;
    //字段名称
    private String fieldName;
    //字段描述
    private String fieldDesc;
    //字段排序
    private String fieldOrder;
    //创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
}
