package org.arch.framework.automate.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.form.api.dto.FormInterfaceSearchDto;
import org.arch.form.api.request.FormInterfaceRequest;
import org.arch.form.crud.entity.FormInterface;
import org.arch.form.crud.service.FormInterfaceService;
import org.arch.framework.crud.CrudBiz;
import org.arch.framework.ums.bean.TokenInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.dcenter.ums.security.core.api.tenant.handler.TenantContextHolder;

import static java.util.Objects.nonNull;

/**
 * 表单接口(FormInterface) 表服务控制器
 *
 * @author lait
 * @date 2021-02-10 15:36:57
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/form/interface")
public class FormInterfaceController implements CrudBiz<FormInterfaceRequest, FormInterface, Long,
        FormInterfaceSearchDto, FormInterfaceSearchDto, FormInterfaceService> {

    private final TenantContextHolder tenantContextHolder;
    private final FormInterfaceService formInterfaceService;

    @Override
    public FormInterface resolver(TokenInfo token, FormInterfaceRequest request) {
        FormInterface entity = new FormInterface();
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
    public FormInterfaceService getCrudService() {
        return formInterfaceService;
    }

    @Override
    public FormInterfaceSearchDto getSearchDto() {
        return new FormInterfaceSearchDto();
    }

}
