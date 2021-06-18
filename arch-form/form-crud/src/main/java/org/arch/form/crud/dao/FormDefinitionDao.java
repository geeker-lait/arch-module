package org.arch.form.crud.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.form.crud.entity.FormDefinition;
import org.arch.form.crud.mapper.FormDefinitionMapper;
import org.arch.framework.crud.CrudDao;
import org.arch.framework.crud.CrudServiceImpl;
import org.springframework.stereotype.Repository;

/**
 * 表单定义(FormDefinition) 表数据库访问层
 *
 * @author lait
 * @date 2021-02-10 15:36:22
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@Repository
public class FormDefinitionDao extends CrudServiceImpl<FormDefinitionMapper, FormDefinition> implements CrudDao<FormDefinition> {
    private final FormDefinitionMapper formDefinitionMapper;
}
