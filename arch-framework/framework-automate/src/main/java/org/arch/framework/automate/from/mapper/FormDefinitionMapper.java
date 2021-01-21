package org.arch.framework.automate.from.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.arch.framework.automate.from.entity.FormDefinition;

/**
 * 表单定义(form_definition)数据Mapper
 *
 * @author lait
 * @since 2021-01-18 20:28:43
 * @description PN15855012581
*/
@Mapper
public interface FormDefinitionMapper extends BaseMapper<FormDefinition> {

}
