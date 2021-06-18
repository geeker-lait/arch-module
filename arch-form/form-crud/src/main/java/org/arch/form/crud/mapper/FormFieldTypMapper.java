package org.arch.form.crud.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import org.apache.ibatis.annotations.Mapper;
import org.arch.form.api.DatasourceConstants;
import org.arch.form.crud.entity.FormFieldTyp;
import org.arch.framework.crud.CrudMapper;

/**
 * 表单字段类型(FormFieldTyp) 表数据库 Mapper 层
 *
 * @author lait
 * @date 2021-02-10 15:36:51
 * @since 1.0.0
 */
@Mapper
@DS(DatasourceConstants.DATASOURCE_MASTER_FORM)
public interface FormFieldTypMapper extends CrudMapper<FormFieldTyp> {

}
