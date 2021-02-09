package org.arch.framework.automate.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.automate.api.dto.FormInstanceSearchDto;
import org.arch.framework.automate.from.entity.FormInstance;
import org.arch.framework.automate.from.service.FormInstanceService;
import org.arch.framework.crud.CrudController;
import org.arch.framework.ums.bean.TokenInfo;
import org.arch.framework.ums.properties.AppProperties;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static java.util.Objects.nonNull;

/**
 * 表单实例(FormInstance) 表服务控制器
 *
 * @author lait
 * @date 2021-02-08 13:25:24
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/form/instance")
public class FormInstanceController implements CrudController<FormInstance, java.lang.Long, FormInstanceSearchDto, FormInstanceService> {

    private final AppProperties appProperties;
    private final FormInstanceService formInstanceService;

    @Override
    public FormInstance resolver(TokenInfo token, FormInstance formInstance) {
        // TODO 默认实现不处理, 根据 TokenInfo 处理 formInstance 后返回 formInstance, 如: tenantId 的处理等.
        if (nonNull(token) && nonNull(token.getTenantId())) {
            formInstance.setTenantId(token.getTenantId());
        } else {
            formInstance.setTenantId(appProperties.getSystemTenantId());
        }
        return formInstance;
    }

    @Override
    public FormInstanceService getCrudService() {
        return formInstanceService;
    }

    @Override
    public FormInstanceSearchDto getSearchDto() {
        return new FormInstanceSearchDto();
    }

}
