package org.arch.form.crud.service;

import com.baomidou.dynamic.datasource.annotation.DS;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.automate.api.Constants;
import org.arch.form.crud.dao.FormLayoutDao;
import org.arch.form.crud.entity.FormLayout;
import org.arch.framework.crud.CrudService;
import org.springframework.stereotype.Service;

/**
 * 表单布局(FormLayout) 表服务层
 *
 * @author lait
 * @date 2021-02-10 15:37:05
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@Service
@DS(Constants.DATASOURCE_MASTER_FORM)
public class FormLayoutService extends CrudService<FormLayout, java.lang.Long> {
    private final FormLayoutDao formLayoutDao = (FormLayoutDao) crudDao;
}
