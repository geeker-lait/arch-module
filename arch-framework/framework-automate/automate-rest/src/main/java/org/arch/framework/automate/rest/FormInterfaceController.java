package org.arch.framework.automate.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudController;
import org.arch.framework.ums.bean.TokenInfo;
import org.arch.framework.automate.api.dto.FormInterfaceSearchDto;
import org.arch.framework.automate.from.entity.FormInterface;
import org.arch.framework.automate.from.service.FormInterfaceService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.dcenter.ums.security.core.api.tenant.handler.TenantContextHolder;

import static java.util.Objects.isNull;
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
public class FormInterfaceController implements CrudController<FormInterface, java.lang.Long, FormInterfaceSearchDto, FormInterfaceService> {

    private final TenantContextHolder tenantContextHolder;
    private final FormInterfaceService formInterfaceService;

    @Override
    public FormInterface resolver(TokenInfo token, FormInterface formInterface) {
        if (isNull(formInterface)) {
            formInterface =  new FormInterface();
        }
        if (nonNull(token) && nonNull(token.getTenantId())) {
            formInterface.setTenantId(token.getTenantId());
        } else {
            formInterface.setTenantId(Integer.parseInt(tenantContextHolder.getTenantId()));
        }
        return formInterface;
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
