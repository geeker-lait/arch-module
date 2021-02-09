package org.arch.framework.automate.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.automate.api.dto.FormFieldSearchDto;
import org.arch.framework.automate.from.entity.FormField;
import org.arch.framework.automate.from.service.FormFieldService;
import org.arch.framework.crud.CrudController;
import org.arch.framework.ums.bean.TokenInfo;
import org.arch.framework.ums.properties.AppProperties;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static java.util.Objects.nonNull;

/**
 * 表单字段(FormField) 表服务控制器
 *
 * @author lait
 * @date 2021-02-08 13:25:14
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/form/field")
public class FormFieldController implements CrudController<FormField, java.lang.Long, FormFieldSearchDto, FormFieldService> {

    private final AppProperties appProperties;
    private final FormFieldService formFieldService;

    @Override
    public FormField resolver(TokenInfo token, FormField formField) {
        // TODO 默认实现不处理, 根据 TokenInfo 处理 formField 后返回 formField, 如: tenantId 的处理等.
        if (nonNull(token) && nonNull(token.getTenantId())) {
            formField.setTenantId(token.getTenantId());
        } else {
            formField.setTenantId(appProperties.getSystemTenantId());
        }
        return formField;
    }

    @Override
    public FormFieldService getCrudService() {
        return formFieldService;
    }

    @Override
    public FormFieldSearchDto getSearchDto() {
        return new FormFieldSearchDto();
    }

}
