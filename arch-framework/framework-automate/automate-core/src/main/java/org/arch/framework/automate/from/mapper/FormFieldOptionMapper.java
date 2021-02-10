package org.arch.framework.automate.from.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.arch.framework.crud.CrudMapper;
import org.arch.framework.automate.from.entity.FormFieldOption;

/**
 * 表单字段选项(FormFieldOption) 表数据库 Mapper 层
 *
 * @author lait
 * @date 2021-02-10 15:36:43
 * @since 1.0.0
 */
@Mapper
public interface FormFieldOptionMapper extends CrudMapper<FormFieldOption> {

}
