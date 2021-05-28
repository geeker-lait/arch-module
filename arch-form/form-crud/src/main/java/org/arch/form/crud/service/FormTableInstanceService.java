package org.arch.form.crud.service;

import com.baomidou.dynamic.datasource.annotation.DS;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.automate.api.Constants;
import org.arch.form.crud.dao.FormTableInstanceDao;
import org.arch.form.crud.entity.FormTableInstance;
import org.arch.framework.crud.CrudService;
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
@DS(Constants.DATASOURCE_MASTER_FORM)
public class FormTableInstanceService extends CrudService<FormTableInstance, java.lang.Long> {
    private final FormTableInstanceDao formTableInstanceDao = (FormTableInstanceDao) crudDao;
}
