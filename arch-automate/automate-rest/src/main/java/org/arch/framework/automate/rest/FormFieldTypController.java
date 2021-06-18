package org.arch.framework.automate.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.form.api.dto.FormFieldTypSearchDto;
import org.arch.form.api.request.FormFieldTypRequest;
import org.arch.form.crud.entity.FormFieldTyp;
import org.arch.form.crud.service.FormFieldTypService;
import org.arch.framework.crud.CrudBiz;
import org.arch.framework.ums.bean.TokenInfo;
import org.springframework.beans.BeanUtils;
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
public class FormFieldTypController implements CrudBiz<FormFieldTypRequest, FormFieldTyp, Long,
        FormFieldTypSearchDto,  FormFieldTypSearchDto, FormFieldTypService> {

    private final TenantContextHolder tenantContextHolder;
    private final FormFieldTypService formFieldTypService;

    @Override
    public FormFieldTyp resolver(TokenInfo token, FormFieldTypRequest request) {
        FormFieldTyp entity = new FormFieldTyp();
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
    public FormFieldTypService getCrudService() {
        return formFieldTypService;
    }

    @Override
    public FormFieldTypSearchDto getSearchDto() {
        return new FormFieldTypSearchDto();
    }

}
