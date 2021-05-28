package org.arch.form.crud.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import org.apache.ibatis.annotations.Mapper;
import org.arch.form.api.Constants;
import org.arch.form.crud.entity.FormField;
import org.arch.framework.crud.CrudMapper;

/**
 * 表单字段(FormField) 表数据库 Mapper 层
 *
 * @author lait
 * @date 2021-02-10 15:36:35
 * @since 1.0.0
 */
@Mapper
@DS(Constants.DATASOURCE_MASTER_FORM)
public interface FormFieldMapper extends CrudMapper<FormField> {

}
