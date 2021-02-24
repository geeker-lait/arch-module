package org.arch.framework.automate.from.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudService;
import org.arch.framework.automate.from.dao.FormTableInstanceDao;
import org.arch.framework.automate.from.entity.FormTableInstance;
import org.springframework.stereotype.Service;

/**
 * 表单实例(FormTableInstance) 表服务层
 *
 * @author lait
 * @date 2021-02-10 15:37:36
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class FormTableInstanceService extends CrudService<FormTableInstance, java.lang.Long> {
    private final FormTableInstanceDao formTableInstanceDao = (FormTableInstanceDao) crudDao;
}