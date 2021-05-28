package org.arch.automate.form.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.automate.form.entity.FormTable;
import org.arch.automate.form.mapper.FormTableMapper;
import org.arch.framework.crud.CrudDao;
import org.arch.framework.crud.CrudServiceImpl;
import org.springframework.stereotype.Repository;

/**
 * 业务表单(FormTable) 表数据库访问层
 *
 * @author lait
 * @date 2021-02-10 15:37:27
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@Repository
public class FormTableDao extends CrudServiceImpl<FormTableMapper, FormTable> implements CrudDao<FormTable> {
    private final FormTableMapper formTableMapper;
}
