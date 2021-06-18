package org.arch.form.crud.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.form.crud.entity.FormFieldOption;
import org.arch.form.crud.mapper.FormFieldOptionMapper;
import org.arch.framework.crud.CrudDao;
import org.arch.framework.crud.CrudServiceImpl;
import org.springframework.stereotype.Repository;

/**
 * 表单字段选项(FormFieldOption) 表数据库访问层
 *
 * @author lait
 * @date 2021-02-10 15:36:40
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@Repository
public class FormFieldOptionDao extends CrudServiceImpl<FormFieldOptionMapper, FormFieldOption> implements CrudDao<FormFieldOption> {
    private final FormFieldOptionMapper formFieldOptionMapper;
}
