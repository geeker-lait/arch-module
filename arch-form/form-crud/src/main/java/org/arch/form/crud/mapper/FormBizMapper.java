package org.arch.form.crud.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import org.apache.ibatis.annotations.Mapper;

import org.arch.form.api.DatasourceConstants;
import org.arch.form.crud.entity.FormBiz;
import org.arch.framework.crud.CrudMapper;

/**
 * 项目业务(FormBiz) 表数据库 Mapper 层
 *
 * @author lait
 * @date 2021-02-10 15:36:18
 * @since 1.0.0
 */
@Mapper
@DS(DatasourceConstants.DATASOURCE_MASTER_FORM)
public interface FormBizMapper extends CrudMapper<FormBiz> {

}
