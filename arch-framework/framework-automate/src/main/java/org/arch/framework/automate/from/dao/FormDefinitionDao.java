package org.arch.framework.automate.from.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.automate.from.entity.FormDefinition;
import org.arch.framework.automate.from.mapper.FormDefinitionMapper;
import org.springframework.stereotype.Repository;

/**
 * 表单定义(form_definition)数据DAO
 *
 * @author lait
 * @description PN15855012581
 * @since 2021-01-18 20:28:43
 */
@Slf4j
@Repository
public class FormDefinitionDao extends ServiceImpl<FormDefinitionMapper, FormDefinition> {

}
