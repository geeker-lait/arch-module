package org.arch.framework.automate.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.automate.api.dto.FormBizSearchDto;
import org.arch.framework.automate.from.entity.FormBiz;
import org.arch.framework.automate.from.service.FormBizService;
import org.arch.framework.crud.CrudController;
import org.arch.framework.ums.bean.TokenInfo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.dcenter.ums.security.core.api.tenant.handler.TenantContextHolder;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/**
 * 项目业务(FormBiz) 表服务控制器
 *
 * @author lait
 * @date 2021-02-10 15:36:16
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/form/biz")
public class FormBizController implements CrudController<FormBiz, java.lang.Long, FormBizSearchDto, FormBizService> {

    private final TenantContextHolder tenantContextHolder;
    private final FormBizService formBizService;

    @Override
    public FormBiz resolver(TokenInfo token, FormBiz formBiz) {
        if (isNull(formBiz)) {
            formBiz =  new FormBiz();
        }
        if (nonNull(token) && nonNull(token.getTenantId())) {
            formBiz.setTenantId(token.getTenantId());
        } else {
            formBiz.setTenantId(Integer.parseInt(tenantContextHolder.getTenantId()));
        }
        return formBiz;
    }

    @Override
    public FormBizService getCrudService() {
        return formBizService;
    }

    @Override
    public FormBizSearchDto getSearchDto() {
        return new FormBizSearchDto();
    }

}
