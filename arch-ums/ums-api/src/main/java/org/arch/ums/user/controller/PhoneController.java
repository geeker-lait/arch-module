package org.arch.ums.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudController;
import org.arch.framework.ums.bean.TokenInfo;
import org.arch.ums.user.dto.PhoneSearchDto;
import org.arch.ums.user.entity.Phone;
import org.arch.ums.user.service.PhoneService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.dcenter.ums.security.core.api.tenant.handler.TenantContextHolder;

import static java.util.Objects.nonNull;

/**
 * 用户电话信息(Phone) 表服务控制器
 *
 * @author YongWu zheng
 * @date 2021-01-30 11:31:29
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/user/phone")
public class PhoneController implements CrudController<Phone, Long, PhoneSearchDto, PhoneService> {

    private final TenantContextHolder tenantContextHolder;
    private final PhoneService phoneService;

    @Override
    public Phone resolver(TokenInfo token, Phone phone) {
        // TODO 默认实现不处理, 根据 TokenInfo 处理 phone 后返回 phone, 如: tenantId 的处理等.
        if (nonNull(token) && nonNull(token.getTenantId())) {
            phone.setTenantId(token.getTenantId());
        }
        else {
            phone.setTenantId(Integer.parseInt(tenantContextHolder.getTenantId()));
        }
        return phone;
    }

    @Override
    public PhoneService getCrudService() {
        return phoneService;
    }

    @Override
    public PhoneSearchDto getSearchDto() {
        return new PhoneSearchDto();
    }

}