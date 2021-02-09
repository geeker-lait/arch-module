package org.arch.framework.automate.from.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.arch.framework.automate.from.entity.FormInstance;
import org.arch.framework.crud.CrudMapper;

/**
 * 表单实例(FormInstance) 表数据库 Mapper 层
 *
 * @author lait
 * @date 2021-02-08 13:25:25
 * @since 1.0.0
 */
@Mapper
public interface FormInstanceMapper extends CrudMapper<FormInstance> {

}
