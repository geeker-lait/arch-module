package org.arch.framework.automate.from.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.automate.from.entity.FormInterface;
import org.arch.framework.automate.from.mapper.FormInterfaceMapper;
import org.arch.framework.crud.CrudDao;
import org.arch.framework.crud.CrudServiceImpl;
import org.springframework.stereotype.Repository;

/**
 * 表单接口(FormInterface) 表数据库访问层
 *
 * @author lait
 * @date 2021-02-10 15:36:56
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@Repository
public class FormInterfaceDao extends CrudServiceImpl<FormInterfaceMapper, FormInterface> implements CrudDao<FormInterface> {
    private final FormInterfaceMapper formInterfaceMapper;
}
