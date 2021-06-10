package org.arch.framework.automate.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.form.api.dto.FormTableInstanceSearchDto;
import org.arch.form.api.request.FormTableInstanceRequest;
import org.arch.form.crud.entity.FormTableInstance;
import org.arch.form.crud.service.FormTableInstanceService;
import org.arch.framework.crud.CrudBiz;
import org.arch.framework.ums.bean.TokenInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.dcenter.ums.security.core.api.tenant.handler.TenantContextHolder;

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
public class FormTableInstanceController implements CrudBiz<FormTableInstanceRequest, FormTableInstance,
        Long, FormTableInstanceSearchDto, FormTableInstanceSearchDto, FormTableInstanceService> {

    private final TenantContextHolder tenantContextHolder;
    private final FormTableInstanceService formTableInstanceService;

    @Override
    public FormTableInstance resolver(TokenInfo token, FormTableInstanceRequest request) {
        FormTableInstance entity = new FormTableInstance();
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
    public FormTableInstanceService getCrudService() {
        return formTableInstanceService;
    }

    @Override
    public FormTableInstanceSearchDto getSearchDto() {
        return new FormTableInstanceSearchDto();
    }

}
