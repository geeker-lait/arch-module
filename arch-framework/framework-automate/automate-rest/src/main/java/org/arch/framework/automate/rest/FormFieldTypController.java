package org.arch.framework.automate.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudController;
import org.arch.framework.ums.bean.TokenInfo;
import org.arch.framework.automate.api.dto.FormFieldTypSearchDto;
import org.arch.framework.automate.from.entity.FormFieldTyp;
import org.arch.framework.automate.from.service.FormFieldTypService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.dcenter.ums.security.core.api.tenant.handler.TenantContextHolder;

import static java.util.Objects.nonNull;

/**
 * 表单字段类型(FormFieldTyp) 表服务控制器
 *
 * @author lait
 * @date 2021-02-10 15:36:50
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/form/field/typ")
public class FormFieldTypController implements CrudController<FormFieldTyp, java.lang.Long, FormFieldTypSearchDto, FormFieldTypService> {

    private final TenantContextHolder tenantContextHolder;
    private final FormFieldTypService formFieldTypService;

    @Override
    public FormFieldTyp resolver(TokenInfo token, FormFieldTyp formFieldTyp) {
        // TODO 默认实现不处理, 根据 TokenInfo 处理 formFieldTyp 后返回 formFieldTyp, 如: tenantId 的处理等.
        if (nonNull(token) && nonNull(token.getTenantId())) {
            formFieldTyp.setTenantId(token.getTenantId());
        } else {
            formFieldTyp.setTenantId(Integer.parseInt(tenantContextHolder.getTenantId()));
        }
        return formFieldTyp;
    }

    @Override
    public FormFieldTypService getCrudService() {
        return formFieldTypService;
    }

    @Override
    public FormFieldTypSearchDto getSearchDto() {
        return new FormFieldTypSearchDto();
    }

}
