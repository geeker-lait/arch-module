package org.arch.form.crud.service;

import com.baomidou.dynamic.datasource.annotation.DS;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.arch.form.api.DatasourceConstants;
import org.arch.form.crud.dao.FormSchemaConfigDao;
import org.arch.form.crud.entity.FormSchemaConfig;
import org.arch.framework.crud.CrudService;
import org.springframework.stereotype.Service;

/**
 * 项目配置(FormSchemaConfig) 表服务层
 *
 * @author lait
 * @date 2021-02-10 15:37:21
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@Service
@DS(DatasourceConstants.DATASOURCE_MASTER_FORM)
public class FormSchemaConfigService extends CrudService<FormSchemaConfig, java.lang.Long> {
    private final FormSchemaConfigDao formSchemaConfigDao = (FormSchemaConfigDao) crudDao;
}
