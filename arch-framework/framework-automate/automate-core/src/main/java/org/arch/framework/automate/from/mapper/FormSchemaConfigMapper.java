package org.arch.framework.automate.from.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.arch.framework.crud.CrudMapper;
import org.arch.framework.automate.from.entity.FormSchemaConfig;

/**
 * 项目配置(FormSchemaConfig) 表数据库 Mapper 层
 *
 * @author lait
 * @date 2021-02-10 15:37:23
 * @since 1.0.0
 */
@Mapper
public interface FormSchemaConfigMapper extends CrudMapper<FormSchemaConfig> {

}