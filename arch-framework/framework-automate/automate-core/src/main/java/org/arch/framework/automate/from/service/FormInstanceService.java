package org.arch.framework.automate.from.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.automate.from.dao.FormInstanceDao;
import org.arch.framework.automate.from.entity.FormInstance;
import org.arch.framework.crud.CrudService;
import org.springframework.stereotype.Service;

/**
 * 表单实例(FormInstance) 表服务层
 *
 * @author lait
 * @date 2021-02-08 13:25:24
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class FormInstanceService extends CrudService<FormInstance, java.lang.Long> {
    private final FormInstanceDao formInstanceDao = (FormInstanceDao) crudDao;
}
