package org.arch.framework.automate.from.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.arch.framework.crud.CrudMapper;
import org.arch.framework.automate.from.entity.FormFieldTyp;

/**
 * 表单字段类型(FormFieldTyp) 表数据库 Mapper 层
 *
 * @author lait
 * @date 2021-02-08 13:25:22
 * @since 1.0.0
 */
@Mapper
public interface FormFieldTypMapper extends CrudMapper<FormFieldTyp> {

}
