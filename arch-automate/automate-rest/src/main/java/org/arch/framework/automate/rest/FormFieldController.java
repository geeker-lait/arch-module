package org.arch.framework.automate.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.form.api.dto.FormFieldSearchDto;
import org.arch.form.api.request.FormFieldRequest;
import org.arch.form.crud.entity.FormField;
import org.arch.form.crud.service.FormFieldService;
import org.arch.framework.crud.CrudBiz;
import org.arch.framework.ums.bean.TokenInfo;
import org.springframework.beans.BeanUtils;
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
public class FormFieldController implements CrudBiz<FormFieldRequest, FormField, Long, FormFieldSearchDto,
        FormFieldService> {

    private final TenantContextHolder tenantContextHolder;
    private final FormFieldService formFieldService;

    @Override
    public FormField resolver(TokenInfo token, FormFieldRequest request) {
        FormField entity = new FormField();
        if (nonNull(request)) {
            BeanUtils.copyProperties(request, entity);
        }
        if (nonNull(token) && nonNull(token.getTenantId())) {
            entity.setTenantId(token.getTenantId());
        } else {
            entity.setTenantId(Integer.parseInt(tenantContextHolder.getTenantId()));
        }
        return entity;
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
