package org.arch.framework.automate.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.automate.api.dto.FormLayoutSearchDto;
import org.arch.framework.automate.api.request.FormLayoutRequest;
import org.arch.automate.form.entity.FormLayout;
import org.arch.automate.form.service.FormLayoutService;
import org.arch.framework.crud.CrudController;
import org.arch.framework.ums.bean.TokenInfo;
import org.springframework.beans.BeanUtils;
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
public class FormLayoutController implements CrudController<FormLayoutRequest, FormLayout, java.lang.Long,
        FormLayoutSearchDto, FormLayoutService> {

    private final TenantContextHolder tenantContextHolder;
    private final FormLayoutService formLayoutService;

    @Override
    public FormLayout resolver(TokenInfo token, FormLayoutRequest request) {
        FormLayout entity = new FormLayout();
        if (nonNull(request)) {
            BeanUtils.copyProperties(request, entity);
        }
        if (nonNull(token) && nonNull(token.getTenantId())) {
            entity.setTenantId(token.getTenantId());
        }
        else {
            entity.setTenantId(Integer.parseInt(tenantContextHolder.getTenantId()));
        }
        return entity;
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
