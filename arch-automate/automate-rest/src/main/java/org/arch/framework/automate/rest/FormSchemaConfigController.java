package org.arch.framework.automate.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.automate.api.dto.FormSchemaConfigSearchDto;
import org.arch.framework.automate.api.request.FormSchemaConfigRequest;
import org.arch.automate.form.entity.FormSchemaConfig;
import org.arch.automate.form.service.FormSchemaConfigService;
import org.arch.framework.crud.CrudController;
import org.arch.framework.ums.bean.TokenInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.dcenter.ums.security.core.api.tenant.handler.TenantContextHolder;

import static java.util.Objects.nonNull;

/**
 * 项目配置(FormSchemaConfig) 表服务控制器
 *
 * @author lait
 * @date 2021-02-10 15:37:21
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/form/schema/config")
public class FormSchemaConfigController implements CrudController<FormSchemaConfigRequest, FormSchemaConfig, java.lang.Long
        , FormSchemaConfigSearchDto, FormSchemaConfigService> {

    private final TenantContextHolder tenantContextHolder;
    private final FormSchemaConfigService formSchemaConfigService;

    @Override
    public FormSchemaConfig resolver(TokenInfo token, FormSchemaConfigRequest request) {
        FormSchemaConfig entity = new FormSchemaConfig();
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
    public FormSchemaConfigService getCrudService() {
        return formSchemaConfigService;
    }

    @Override
    public FormSchemaConfigSearchDto getSearchDto() {
        return new FormSchemaConfigSearchDto();
    }

}
