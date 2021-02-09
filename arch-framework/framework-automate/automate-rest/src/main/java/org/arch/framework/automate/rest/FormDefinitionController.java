package org.arch.framework.automate.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.automate.api.dto.FormDefinitionSearchDto;
import org.arch.framework.automate.from.entity.FormDefinition;
import org.arch.framework.automate.from.service.FormDefinitionService;
import org.arch.framework.crud.CrudController;
import org.arch.framework.ums.bean.TokenInfo;
import org.arch.framework.ums.properties.AppProperties;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static java.util.Objects.nonNull;

/**
 * 表单定义(FormDefinition) 表服务控制器
 *
 * @author lait
 * @date 2021-02-08 13:25:11
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/form/definition")
public class FormDefinitionController implements CrudController<FormDefinition, java.lang.Long, FormDefinitionSearchDto, FormDefinitionService> {

    private final AppProperties appProperties;
    private final FormDefinitionService formDefinitionService;

    @Override
    public FormDefinition resolver(TokenInfo token, FormDefinition formDefinition) {
        // TODO 默认实现不处理, 根据 TokenInfo 处理 formDefinition 后返回 formDefinition, 如: tenantId 的处理等.
        if (nonNull(token) && nonNull(token.getTenantId())) {
            formDefinition.setTenantId(token.getTenantId());
        } else {
            formDefinition.setTenantId(appProperties.getSystemTenantId());
        }
        return formDefinition;
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
