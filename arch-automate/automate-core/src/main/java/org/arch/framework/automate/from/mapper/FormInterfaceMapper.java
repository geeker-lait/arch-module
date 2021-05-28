package org.arch.framework.automate.from.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import org.apache.ibatis.annotations.Mapper;
import org.arch.framework.automate.api.Constants;
import org.arch.framework.automate.from.entity.FormInterface;
import org.arch.framework.crud.CrudMapper;

/**
 * 表单接口(FormInterface) 表数据库 Mapper 层
 *
 * @author lait
 * @date 2021-02-10 15:36:59
 * @since 1.0.0
 */
@Mapper
@DS(Constants.DATASOURCE_MASTER_FORM)
public interface FormInterfaceMapper extends CrudMapper<FormInterface> {

}
