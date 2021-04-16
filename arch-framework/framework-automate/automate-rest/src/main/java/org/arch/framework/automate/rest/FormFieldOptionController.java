package org.arch.framework.automate.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudController;
import org.arch.framework.ums.bean.TokenInfo;
import org.arch.framework.automate.api.dto.FormFieldOptionSearchDto;
import org.arch.framework.automate.from.entity.FormFieldOption;
import org.arch.framework.automate.from.service.FormFieldOptionService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.dcenter.ums.security.core.api.tenant.handler.TenantContextHolder;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/**
 * 表单字段选项(FormFieldOption) 表服务控制器
 *
 * @author lait
 * @date 2021-02-10 15:36:42
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/form/field/option")
public class FormFieldOptionController implements CrudController<FormFieldOption, java.lang.Long, FormFieldOptionSearchDto, FormFieldOptionService> {

    private final TenantContextHolder tenantContextHolder;
    private final FormFieldOptionService formFieldOptionService;

    @Override
    public FormFieldOption resolver(TokenInfo token, FormFieldOption formFieldOption) {
        if (isNull(formFieldOption)) {
            formFieldOption =  new FormFieldOption();
        }
        if (nonNull(token) && nonNull(token.getTenantId())) {
            formFieldOption.setTenantId(token.getTenantId());
        } else {
            formFieldOption.setTenantId(Integer.parseInt(tenantContextHolder.getTenantId()));
        }
        return formFieldOption;
    }

    @Override
    public FormFieldOptionService getCrudService() {
        return formFieldOptionService;
    }

    @Override
    public FormFieldOptionSearchDto getSearchDto() {
        return new FormFieldOptionSearchDto();
    }

}
