package org.arch.framework.automate.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudController;
import org.arch.framework.ums.bean.TokenInfo;
import org.arch.framework.automate.api.dto.FormSchemaConfigSearchDto;
import org.arch.framework.automate.from.entity.FormSchemaConfig;
import org.arch.framework.automate.from.service.FormSchemaConfigService;
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
public class FormSchemaConfigController implements CrudController<FormSchemaConfig, java.lang.Long, FormSchemaConfigSearchDto, FormSchemaConfigService> {

    private final TenantContextHolder tenantContextHolder;
    private final FormSchemaConfigService formSchemaConfigService;

    @Override
    public FormSchemaConfig resolver(TokenInfo token, FormSchemaConfig formSchemaConfig) {
        // TODO 默认实现不处理, 根据 TokenInfo 处理 formSchemaConfig 后返回 formSchemaConfig, 如: tenantId 的处理等.
        if (nonNull(token) && nonNull(token.getTenantId())) {
            formSchemaConfig.setTenantId(token.getTenantId());
        } else {
            formSchemaConfig.setTenantId(Integer.parseInt(tenantContextHolder.getTenantId()));
        }
        return formSchemaConfig;
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
