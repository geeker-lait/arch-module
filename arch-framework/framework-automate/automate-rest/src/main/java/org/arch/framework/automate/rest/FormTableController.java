package org.arch.framework.automate.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.automate.api.dto.FormTableSearchDto;
import org.arch.framework.automate.api.request.FormTableRequest;
import org.arch.framework.automate.from.entity.FormTable;
import org.arch.framework.automate.from.service.FormTableService;
import org.arch.framework.crud.CrudController;
import org.arch.framework.ums.bean.TokenInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.dcenter.ums.security.core.api.tenant.handler.TenantContextHolder;

import static java.util.Objects.nonNull;

/**
 * 业务表单(FormTable) 表服务控制器
 *
 * @author lait
 * @date 2021-02-10 15:37:29
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/form/table")
public class FormTableController implements CrudController<FormTableRequest, FormTable, java.lang.Long, FormTableSearchDto,
        FormTableService> {

    private final TenantContextHolder tenantContextHolder;
    private final FormTableService formTableService;

    @Override
    public FormTable resolver(TokenInfo token, FormTableRequest request) {
        FormTable entity = new FormTable();
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
    public FormTableService getCrudService() {
        return formTableService;
    }

    @Override
    public FormTableSearchDto getSearchDto() {
        return new FormTableSearchDto();
    }

}
