package org.arch.framework.automate.from.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudService;
import org.arch.framework.automate.from.dao.FormFieldDao;
import org.arch.framework.automate.from.entity.FormField;
import org.springframework.stereotype.Service;

/**
 * 表单字段(FormField) 表服务层
 *
 * @author lait
 * @date 2021-02-10 15:36:33
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class FormFieldService extends CrudService<FormField, java.lang.Long> {
    private final FormFieldDao formFieldDao = (FormFieldDao) crudDao;
}
