package org.arch.framework.automate.from.dao;

import lombok.extern.slf4j.Slf4j;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.arch.framework.automate.from.entity.FormField;
import org.arch.framework.automate.from.mapper.FormFieldMapper;
import org.springframework.stereotype.Repository;

/**
 * 表单字段(form_field)数据DAO
 *
 * @author lait
 * @since 2021-01-18 20:28:43
 * @description PN15855012581
 */
@Slf4j
@Repository
public class FormFieldDao extends ServiceImpl<FormFieldMapper, FormField> {

}
