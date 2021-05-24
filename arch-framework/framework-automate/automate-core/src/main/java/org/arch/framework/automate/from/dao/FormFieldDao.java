package org.arch.framework.automate.from.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.automate.from.entity.FormField;
import org.arch.framework.automate.from.mapper.FormFieldMapper;
import org.arch.framework.crud.CrudDao;
import org.arch.framework.crud.CrudServiceImpl;
import org.springframework.stereotype.Repository;

/**
 * 表单字段(FormField) 表数据库访问层
 *
 * @author lait
 * @date 2021-02-10 15:36:31
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@Repository
public class FormFieldDao extends CrudServiceImpl<FormFieldMapper, FormField> implements CrudDao<FormField> {
    private final FormFieldMapper formFieldMapper;
}
