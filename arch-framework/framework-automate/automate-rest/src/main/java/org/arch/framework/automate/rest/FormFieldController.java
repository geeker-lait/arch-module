package org.arch.framework.automate.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudController;
import org.arch.framework.ums.bean.TokenInfo;
import org.arch.framework.automate.from.dto.FormFieldSearchDto;
import org.arch.framework.automate.from.entity.FormField;
import org.arch.framework.automate.from.service.FormFieldService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.dcenter.ums.security.core.api.tenant.handler.TenantContextHolder;

import static java.util.Objects.nonNull;

/**
 * 表单字段(FormField) 表服务控制器
 *
 * @author lait
 * @date 2021-02-10 15:36:33
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/form/field")
public class FormFieldController implements CrudController<FormField, java.lang.Long, FormFieldSearchDto, FormFieldService> {

    private final TenantContextHolder tenantContextHolder;
    private final FormFieldService formFieldService;

    @Override
    public FormField resolver(TokenInfo token, FormField formField) {
        // TODO 默认实现不处理, 根据 TokenInfo 处理 formField 后返回 formField, 如: tenantId 的处理等.
        if (nonNull(token) && nonNull(token.getTenantId())) {
            formField.setTenantId(token.getTenantId());
        } else {
            formField.setTenantId(Integer.parseInt(tenantContextHolder.getTenantId()));
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
