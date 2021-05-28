package org.arch.framework.automate.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.form.api.dto.FormDefinitionSearchDto;
import org.arch.form.api.request.FormDefinitionRequest;
import org.arch.form.crud.entity.FormDefinition;
import org.arch.form.crud.service.FormDefinitionService;
import org.arch.framework.crud.CrudController;
import org.arch.framework.ums.bean.TokenInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.dcenter.ums.security.core.api.tenant.handler.TenantContextHolder;

import static java.util.Objects.nonNull;

/**
 * 表单定义(FormDefinition) 表服务控制器
 *
 * @author lait
 * @date 2021-02-10 15:36:24
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/form/definition")
public class FormDefinitionController implements CrudController<FormDefinitionRequest, FormDefinition, java.lang.Long,
        FormDefinitionSearchDto, FormDefinitionService> {

    private final TenantContextHolder tenantContextHolder;
    private final FormDefinitionService formDefinitionService;

    @Override
    public FormDefinition resolver(TokenInfo token, FormDefinitionRequest request) {
        FormDefinition entity = new FormDefinition();
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
    public FormDefinitionService getCrudService() {
        return formDefinitionService;
    }

    @Override
    public FormDefinitionSearchDto getSearchDto() {
        return new FormDefinitionSearchDto();
    }

}
