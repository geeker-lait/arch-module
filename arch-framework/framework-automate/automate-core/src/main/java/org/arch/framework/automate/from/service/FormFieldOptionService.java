package org.arch.framework.automate.from.service;

import com.baomidou.dynamic.datasource.annotation.DS;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.automate.api.Constants;
import org.arch.framework.crud.CrudService;
import org.arch.framework.automate.from.dao.FormFieldOptionDao;
import org.arch.framework.automate.from.entity.FormFieldOption;
import org.springframework.stereotype.Service;

/**
 * 表单字段选项(FormFieldOption) 表服务层
 *
 * @author lait
 * @date 2021-02-10 15:36:41
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@Service
@DS(Constants.DATASOURCE_MASTER_FORM)
public class FormFieldOptionService extends CrudService<FormFieldOption, java.lang.Long> {
    private final FormFieldOptionDao formFieldOptionDao = (FormFieldOptionDao) crudDao;
}
