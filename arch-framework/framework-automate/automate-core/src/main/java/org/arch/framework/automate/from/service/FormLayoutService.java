package org.arch.framework.automate.from.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudService;
import org.arch.framework.automate.from.dao.FormLayoutDao;
import org.arch.framework.automate.from.entity.FormLayout;
import org.springframework.stereotype.Service;

/**
 * 表单布局(FormLayout) 表服务层
 *
 * @author lait
 * @date 2021-02-08 13:25:27
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class FormLayoutService extends CrudService<FormLayout, java.lang.Long> {
    private final FormLayoutDao formLayoutDao = (FormLayoutDao) crudDao;
}
