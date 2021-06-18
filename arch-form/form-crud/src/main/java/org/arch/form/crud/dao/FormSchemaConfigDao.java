package org.arch.form.crud.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.form.crud.entity.FormSchemaConfig;
import org.arch.form.crud.mapper.FormSchemaConfigMapper;
import org.arch.framework.crud.CrudDao;
import org.arch.framework.crud.CrudServiceImpl;
import org.springframework.stereotype.Repository;

/**
 * 项目配置(FormSchemaConfig) 表数据库访问层
 *
 * @author lait
 * @date 2021-02-10 15:37:19
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@Repository
public class FormSchemaConfigDao extends CrudServiceImpl<FormSchemaConfigMapper, FormSchemaConfig> implements CrudDao<FormSchemaConfig> {
    private final FormSchemaConfigMapper formSchemaConfigMapper;
}
