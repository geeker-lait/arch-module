package org.arch.framework.automate.from.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.automate.from.entity.FormLayout;
import org.arch.framework.automate.from.mapper.FormLayoutMapper;
import org.arch.framework.crud.CrudDao;
import org.arch.framework.crud.CrudServiceImpl;
import org.springframework.stereotype.Repository;

/**
 * 表单布局(FormLayout) 表数据库访问层
 *
 * @author lait
 * @date 2021-02-10 15:37:04
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@Repository
public class FormLayoutDao extends CrudServiceImpl<FormLayoutMapper, FormLayout> implements CrudDao<FormLayout> {
    private final FormLayoutMapper formLayoutMapper;
}
