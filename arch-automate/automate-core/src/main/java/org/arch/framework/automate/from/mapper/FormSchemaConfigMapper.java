package org.arch.framework.automate.from.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import org.apache.ibatis.annotations.Mapper;
import org.arch.framework.automate.api.Constants;
import org.arch.framework.automate.from.entity.FormSchemaConfig;
import org.arch.framework.crud.CrudMapper;

/**
 * 项目配置(FormSchemaConfig) 表数据库 Mapper 层
 *
 * @author lait
 * @date 2021-02-10 15:37:23
 * @since 1.0.0
 */
@Mapper
@DS(Constants.DATASOURCE_MASTER_FORM)
public interface FormSchemaConfigMapper extends CrudMapper<FormSchemaConfig> {

}
