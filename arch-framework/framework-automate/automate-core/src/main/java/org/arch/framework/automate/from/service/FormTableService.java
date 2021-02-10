package org.arch.framework.automate.from.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudService;
import org.arch.framework.automate.from.dao.FormTableDao;
import org.arch.framework.automate.from.entity.FormTable;
import org.springframework.stereotype.Service;

/**
 * 业务表单(FormTable) 表服务层
 *
 * @author lait
 * @date 2021-02-10 15:37:29
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class FormTableService extends CrudService<FormTable, java.lang.Long> {
    private final FormTableDao formTableDao = (FormTableDao) crudDao;
}
