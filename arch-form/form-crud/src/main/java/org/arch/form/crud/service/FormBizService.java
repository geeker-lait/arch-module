package org.arch.form.crud.service;

import com.baomidou.dynamic.datasource.annotation.DS;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.automate.api.Constants;
import org.arch.form.crud.dao.FormBizDao;
import org.arch.form.crud.entity.FormBiz;
import org.arch.framework.crud.CrudService;
import org.springframework.stereotype.Service;

/**
 * 项目业务(FormBiz) 表服务层
 *
 * @author lait
 * @date 2021-02-10 15:36:16
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@Service
@DS(Constants.DATASOURCE_MASTER_FORM)
public class FormBizService extends CrudService<FormBiz, java.lang.Long> {
    private final FormBizDao formBizDao = (FormBizDao) crudDao;
}
