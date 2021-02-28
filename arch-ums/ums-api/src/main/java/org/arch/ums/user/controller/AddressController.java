package org.arch.ums.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudController;
import org.arch.framework.ums.bean.TokenInfo;
import org.arch.ums.user.dto.AddressSearchDto;
import org.arch.ums.user.entity.Address;
import org.arch.ums.user.service.AddressService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.dcenter.ums.security.core.api.tenant.handler.TenantContextHolder;

import static java.util.Objects.nonNull;

/**
 * 用户地址表(Address) 表服务控制器
 *
 * @author YongWu zheng
 * @date 2021-02-26 23:19:11
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/user/address")
public class AddressController implements CrudController<Address, java.lang.Long, AddressSearchDto, AddressService> {

    private final TenantContextHolder tenantContextHolder;
    private final AddressService addressService;

    @Override
    public Address resolver(TokenInfo token, Address address) {
        if (nonNull(token) && nonNull(token.getTenantId())) {
            address.setTenantId(token.getTenantId());
        }
        else {
            address.setTenantId(Integer.parseInt(tenantContextHolder.getTenantId()));
        }
        return address;
    }

    @Override
    public AddressService getCrudService() {
        return addressService;
    }

    @Override
    public AddressSearchDto getSearchDto() {
        return new AddressSearchDto();
    }

}
