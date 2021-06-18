package org.arch.form.crud.service;

import com.baomidou.dynamic.datasource.annotation.DS;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.arch.form.api.DatasourceConstants;
import org.arch.form.crud.dao.FormFieldDao;
import org.arch.form.crud.entity.FormField;
import org.arch.framework.crud.CrudService;
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
@DS(DatasourceConstants.DATASOURCE_MASTER_FORM)
public class FormFieldService extends CrudService<FormField, java.lang.Long> {
    private final FormFieldDao formFieldDao = (FormFieldDao) crudDao;
}
