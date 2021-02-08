package org.arch.framework.automate.from.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudDao;
import org.arch.framework.crud.CrudServiceImpl;
import org.arch.framework.automate.from.entity.FormInstance;
import org.arch.framework.automate.from.mapper.FormInstanceMapper;
import org.springframework.stereotype.Repository;

/**
 * 表单实例(FormInstance) 表数据库访问层
 *
 * @author lait
 * @date 2021-02-08 13:25:24
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@Repository
public class FormInstanceDao extends CrudServiceImpl<FormInstanceMapper, FormInstance> implements CrudDao<FormInstance> {
    private final FormInstanceMapper formInstanceMapper;
}
