package org.arch.framework.automate.from.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudService;
import org.arch.framework.automate.from.dao.FormSchemaDao;
import org.arch.framework.automate.from.entity.FormSchema;
import org.springframework.stereotype.Service;

/**
 * 表单schema(FormSchema) 表服务层
 *
 * @author lait
 * @date 2021-02-10 15:37:13
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class FormSchemaService extends CrudService<FormSchema, java.lang.Long> {
    private final FormSchemaDao formSchemaDao = (FormSchemaDao) crudDao;
}
