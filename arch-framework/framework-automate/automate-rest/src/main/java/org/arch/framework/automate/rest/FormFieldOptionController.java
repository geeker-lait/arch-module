package org.arch.framework.automate.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.automate.api.dto.FormFieldOptionSearchDto;
import org.arch.framework.automate.from.entity.FormFieldOption;
import org.arch.framework.automate.from.service.FormFieldOptionService;
import org.arch.framework.crud.CrudController;
import org.arch.framework.ums.bean.TokenInfo;
import org.arch.framework.ums.properties.AppProperties;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static java.util.Objects.nonNull;

/**
 * 表单字段选项(FormFieldOption) 表服务控制器
 *
 * @author lait
 * @date 2021-02-08 13:25:18
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/form/field/option")
public class FormFieldOptionController implements CrudController<FormFieldOption, java.lang.Long, FormFieldOptionSearchDto, FormFieldOptionService> {

    private final AppProperties appProperties;
    private final FormFieldOptionService formFieldOptionService;

    @Override
    public FormFieldOption resolver(TokenInfo token, FormFieldOption formFieldOption) {
        // TODO 默认实现不处理, 根据 TokenInfo 处理 formFieldOption 后返回 formFieldOption, 如: tenantId 的处理等.
        if (nonNull(token) && nonNull(token.getTenantId())) {
            formFieldOption.setTenantId(token.getTenantId());
        } else {
            formFieldOption.setTenantId(appProperties.getSystemTenantId());
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
