package org.arch.framework.automate.from.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.arch.framework.automate.from.entity.FormField;

/**
 * 表单字段(form_field)数据Mapper
 *
 * @author lait
 * @since 2021-01-18 20:28:43
 * @description PN15855012581
*/
@Mapper
public interface FormFieldMapper extends BaseMapper<FormField> {

}
