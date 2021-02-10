package org.arch.framework.automate.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudController;
import org.arch.framework.ums.bean.TokenInfo;
import org.arch.framework.automate.from.dto.FormLayoutSearchDto;
import org.arch.framework.automate.from.entity.FormLayout;
import org.arch.framework.automate.from.service.FormLayoutService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.dcenter.ums.security.core.api.tenant.handler.TenantContextHolder;

import static java.util.Objects.nonNull;

/**
 * 表单布局(FormLayout) 表服务控制器
 *
 * @author lait
 * @date 2021-02-10 15:37:06
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/form/layout")
public class FormLayoutController implements CrudController<FormLayout, java.lang.Long, FormLayoutSearchDto, FormLayoutService> {

    private final TenantContextHolder tenantContextHolder;
    private final FormLayoutService formLayoutService;

    @Override
    public FormLayout resolver(TokenInfo token, FormLayout formLayout) {
        // TODO 默认实现不处理, 根据 TokenInfo 处理 formLayout 后返回 formLayout, 如: tenantId 的处理等.
        if (nonNull(token) && nonNull(token.getTenantId())) {
            formLayout.setTenantId(token.getTenantId());
        } else {
            formLayout.setTenantId(Integer.parseInt(tenantContextHolder.getTenantId()));
        }
        return formLayout;
    }

    @Override
    public FormLayoutService getCrudService() {
        return formLayoutService;
    }

    @Override
    public FormLayoutSearchDto getSearchDto() {
        return new FormLayoutSearchDto();
    }

}
