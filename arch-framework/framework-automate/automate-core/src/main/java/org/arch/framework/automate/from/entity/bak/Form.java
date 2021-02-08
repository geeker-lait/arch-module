package org.arch.framework.automate.from.entity.bak;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
public class Form {
    //表单ID
    private Integer formId;
    //表单名称
    private String formName;
    //表单描述
    private String formDesc;
    //表单对应数据库中生成的table name
    private String formTableName;
    //创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;
}
