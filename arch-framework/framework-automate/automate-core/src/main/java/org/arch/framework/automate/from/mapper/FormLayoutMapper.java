package org.arch.framework.automate.from.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.arch.framework.automate.from.entity.FormLayout;
import org.arch.framework.crud.CrudMapper;

/**
 * 表单布局(FormLayout) 表数据库 Mapper 层
 *
 * @author lait
 * @date 2021-02-08 13:25:28
 * @since 1.0.0
 */
@Mapper
public interface FormLayoutMapper extends CrudMapper<FormLayout> {

}
