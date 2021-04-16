package org.arch.framework.automate.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudController;
import org.arch.framework.ums.bean.TokenInfo;
import org.arch.framework.automate.api.dto.FormTableInstanceSearchDto;
import org.arch.framework.automate.from.entity.FormTableInstance;
import org.arch.framework.automate.from.service.FormTableInstanceService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.dcenter.ums.security.core.api.tenant.handler.TenantContextHolder;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/**
 * 表单实例(FormTableInstance) 表服务控制器
 *
 * @author lait
 * @date 2021-02-10 15:37:37
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/form/table/instance")
public class FormTableInstanceController implements CrudController<FormTableInstance, java.lang.Long, FormTableInstanceSearchDto, FormTableInstanceService> {

    private final TenantContextHolder tenantContextHolder;
    private final FormTableInstanceService formTableInstanceService;

    @Override
    public FormTableInstance resolver(TokenInfo token, FormTableInstance formTableInstance) {
        if (isNull(formTableInstance)) {
            formTableInstance =  new FormTableInstance();
        }
        if (nonNull(token) && nonNull(token.getTenantId())) {
            formTableInstance.setTenantId(token.getTenantId());
        } else {
            formTableInstance.setTenantId(Integer.parseInt(tenantContextHolder.getTenantId()));
        }
        return formTableInstance;
    }

    @Override
    public FormTableInstanceService getCrudService() {
        return formTableInstanceService;
    }

    @Override
    public FormTableInstanceSearchDto getSearchDto() {
        return new FormTableInstanceSearchDto();
    }

}
