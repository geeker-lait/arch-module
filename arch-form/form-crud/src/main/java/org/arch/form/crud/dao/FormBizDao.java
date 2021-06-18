package org.arch.form.crud.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.form.crud.entity.FormBiz;
import org.arch.form.crud.mapper.FormBizMapper;
import org.arch.framework.crud.CrudDao;
import org.arch.framework.crud.CrudServiceImpl;
import org.springframework.stereotype.Repository;

/**
 * 项目业务(FormBiz) 表数据库访问层
 *
 * @author lait
 * @date 2021-02-10 15:36:14
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@Repository
public class FormBizDao extends CrudServiceImpl<FormBizMapper, FormBiz> implements CrudDao<FormBiz> {
    private final FormBizMapper formBizMapper;
}