package org.arch.framework.automate.from.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudDao;
import org.arch.framework.crud.CrudServiceImpl;
import org.arch.framework.automate.from.entity.FormFieldTyp;
import org.arch.framework.automate.from.mapper.FormFieldTypMapper;
import org.springframework.stereotype.Repository;

/**
 * 表单字段类型(FormFieldTyp) 表数据库访问层
 *
 * @author lait
 * @date 2021-02-10 15:36:48
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@Repository
public class FormFieldTypDao extends CrudServiceImpl<FormFieldTypMapper, FormFieldTyp> implements CrudDao<FormFieldTyp> {
    private final FormFieldTypMapper formFieldTypMapper;
}
