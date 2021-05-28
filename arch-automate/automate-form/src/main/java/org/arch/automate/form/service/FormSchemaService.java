package org.arch.automate.form.service;

import com.baomidou.dynamic.datasource.annotation.DS;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.automate.api.Constants;
import org.arch.automate.form.dao.FormSchemaDao;
import org.arch.automate.form.entity.FormSchema;
import org.arch.framework.crud.CrudService;
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
@DS(Constants.DATASOURCE_MASTER_FORM)
public class FormSchemaService extends CrudService<FormSchema, java.lang.Long> {
    private final FormSchemaDao formSchemaDao = (FormSchemaDao) crudDao;
}
