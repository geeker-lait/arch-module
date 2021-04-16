package org.arch.framework.automate.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudController;
import org.arch.framework.ums.bean.TokenInfo;
import org.arch.framework.automate.api.dto.FormSchemaSearchDto;
import org.arch.framework.automate.from.entity.FormSchema;
import org.arch.framework.automate.from.service.FormSchemaService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.dcenter.ums.security.core.api.tenant.handler.TenantContextHolder;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/**
 * 表单schema(FormSchema) 表服务控制器
 *
 * @author lait
 * @date 2021-02-10 15:37:13
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/form/schema")
public class FormSchemaController implements CrudController<FormSchema, java.lang.Long, FormSchemaSearchDto, FormSchemaService> {

    private final TenantContextHolder tenantContextHolder;
    private final FormSchemaService formSchemaService;

    @Override
    public FormSchema resolver(TokenInfo token, FormSchema formSchema) {
        if (isNull(formSchema)) {
            formSchema =  new FormSchema();
        }
        if (nonNull(token) && nonNull(token.getTenantId())) {
            formSchema.setTenantId(token.getTenantId());
        } else {
            formSchema.setTenantId(Integer.parseInt(tenantContextHolder.getTenantId()));
        }
        return formSchema;
    }

    @Override
    public FormSchemaService getCrudService() {
        return formSchemaService;
    }

    @Override
    public FormSchemaSearchDto getSearchDto() {
        return new FormSchemaSearchDto();
    }

}
