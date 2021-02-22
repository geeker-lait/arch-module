package org.arch.framework.automate.from.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudService;
import org.arch.framework.automate.from.dao.FormInterfaceDao;
import org.arch.framework.automate.from.entity.FormInterface;
import org.springframework.stereotype.Service;

/**
 * 表单接口(FormInterface) 表服务层
 *
 * @author lait
 * @date 2021-02-10 15:36:57
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class FormInterfaceService extends CrudService<FormInterface, java.lang.Long> {
    private final FormInterfaceDao formInterfaceDao = (FormInterfaceDao) crudDao;
}
