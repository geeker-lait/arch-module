package org.arch.framework.automate.from.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudDao;
import org.arch.framework.crud.CrudServiceImpl;
import org.arch.framework.automate.from.entity.FormFieldOption;
import org.arch.framework.automate.from.mapper.FormFieldOptionMapper;
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
