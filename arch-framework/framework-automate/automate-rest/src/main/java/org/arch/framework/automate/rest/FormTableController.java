package org.arch.framework.automate.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudController;
import org.arch.framework.ums.bean.TokenInfo;
import org.arch.framework.automate.from.dto.FormTableSearchDto;
import org.arch.framework.automate.from.entity.FormTable;
import org.arch.framework.automate.from.service.FormTableService;
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
public class FormTableController implements CrudController<FormTable, java.lang.Long, FormTableSearchDto, FormTableService> {

    private final TenantContextHolder tenantContextHolder;
    private final FormTableService formTableService;

    @Override
    public FormTable resolver(TokenInfo token, FormTable formTable) {
        // TODO 默认实现不处理, 根据 TokenInfo 处理 formTable 后返回 formTable, 如: tenantId 的处理等.
        if (nonNull(token) && nonNull(token.getTenantId())) {
            formTable.setTenantId(token.getTenantId());
        } else {
            formTable.setTenantId(Integer.parseInt(tenantContextHolder.getTenantId()));
        }
        return formTable;
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
