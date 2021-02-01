package org.arch.ums.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudController;
import org.arch.framework.ums.bean.TokenInfo;
import org.arch.framework.ums.properties.AppProperties;
import org.arch.ums.user.dto.AddressSearchDto;
import org.arch.ums.user.entity.Address;
import org.arch.ums.user.service.AddressService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static java.util.Objects.nonNull;

/**
 * 用户地址表(Address) 表服务控制器
 *
 * @author YongWu zheng
 * @date 2021-01-30 11:31:40
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/user/address")
public class AddressController implements CrudController<Address, Long, AddressSearchDto, AddressService> {

    private final AppProperties appProperties;
    private final AddressService addressService;

    @Override
    public Address resolver(TokenInfo token, Address address) {
        // TODO 默认实现不处理, 根据 TokenInfo 处理 address 后返回 address, 如: tenantId 的处理等.
        if (nonNull(token) && nonNull(token.getTenantId())) {
            address.setTenantId(token.getTenantId());
        }
        else {
            address.setTenantId(appProperties.getSystemTenantId());
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