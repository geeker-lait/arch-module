package org.arch.framework.automate.from.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.arch.framework.automate.from.entity.FormFieldOption;
import org.arch.framework.crud.CrudMapper;

/**
 * 表单字段选项(FormFieldOption) 表数据库 Mapper 层
 *
 * @author lait
 * @date 2021-02-08 13:25:19
 * @since 1.0.0
 */
@Mapper
public interface FormFieldOptionMapper extends CrudMapper<FormFieldOption> {

}
