package org.arch.framework.automate.from.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudService;
import org.arch.framework.automate.from.dao.FormDefinitionDao;
import org.arch.framework.automate.from.entity.FormDefinition;
import org.springframework.stereotype.Service;

/**
 * 表单定义(FormDefinition) 表服务层
 *
 * @author lait
 * @date 2021-02-08 13:25:11
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class FormDefinitionService extends CrudService<FormDefinition, java.lang.Long> {
    private final FormDefinitionDao formDefinitionDao = (FormDefinitionDao) crudDao;
}
