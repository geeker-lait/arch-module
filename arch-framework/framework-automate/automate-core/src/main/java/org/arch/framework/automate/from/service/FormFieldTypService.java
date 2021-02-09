package org.arch.framework.automate.from.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.automate.from.dao.FormFieldTypDao;
import org.arch.framework.automate.from.entity.FormFieldTyp;
import org.arch.framework.crud.CrudService;
import org.springframework.stereotype.Service;

/**
 * 表单字段类型(FormFieldTyp) 表服务层
 *
 * @author lait
 * @date 2021-02-08 13:25:21
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class FormFieldTypService extends CrudService<FormFieldTyp, java.lang.Long> {
    private final FormFieldTypDao formFieldTypDao = (FormFieldTypDao) crudDao;
}
