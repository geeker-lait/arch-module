package org.arch.automate.form.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.automate.form.entity.FormTableInstance;
import org.arch.automate.form.mapper.FormTableInstanceMapper;
import org.arch.framework.crud.CrudDao;
import org.arch.framework.crud.CrudServiceImpl;
import org.springframework.stereotype.Repository;

/**
 * 表单实例(FormTableInstance) 表数据库访问层
 *
 * @author lait
 * @date 2021-02-10 15:37:35
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@Repository
public class FormTableInstanceDao extends CrudServiceImpl<FormTableInstanceMapper, FormTableInstance> implements CrudDao<FormTableInstance> {
    private final FormTableInstanceMapper formTableInstanceMapper;
}
