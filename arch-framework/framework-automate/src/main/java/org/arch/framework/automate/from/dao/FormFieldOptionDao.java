package org.arch.framework.automate.from.dao;

import lombok.extern.slf4j.Slf4j;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.arch.framework.automate.from.entity.FormFieldOption;
import org.arch.framework.automate.from.mapper.FormFieldOptionMapper;
import org.springframework.stereotype.Repository;

/**
 * 表单字段选项(form_field_option)数据DAO
 *
 * @author lait
 * @since 2021-01-18 20:28:43
 * @description PN15855012581
 */
@Slf4j
@Repository
public class FormFieldOptionDao extends ServiceImpl<FormFieldOptionMapper, FormFieldOption> {

}
