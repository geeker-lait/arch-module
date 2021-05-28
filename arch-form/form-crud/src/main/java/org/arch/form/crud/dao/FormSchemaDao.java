package org.arch.form.crud.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.form.crud.entity.FormSchema;
import org.arch.form.crud.mapper.FormSchemaMapper;
import org.arch.framework.crud.CrudDao;
import org.arch.framework.crud.CrudServiceImpl;
import org.springframework.stereotype.Repository;

/**
 * 表单schema(FormSchema) 表数据库访问层
 *
 * @author lait
 * @date 2021-02-10 15:37:12
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@Repository
public class FormSchemaDao extends CrudServiceImpl<FormSchemaMapper, FormSchema> implements CrudDao<FormSchema> {
    private final FormSchemaMapper formSchemaMapper;
}
