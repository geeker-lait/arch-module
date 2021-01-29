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

/**
 * 用户地址表(Address) 表服务控制器
 *
 * @author YongWu zheng
 * @date 2021-01-29 21:45:25
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/user/address")
public class AddressController implements CrudController<Address, java.lang.Long, AddressSearchDto, AddressService> {

    private final AddressService addressService;

    @Override
    public Address resolver(TokenInfo token, Address address) {
        // TODO 默认实现不处理, 根据 TokenInfo 处理 address 后返回 address, 如: tenantId 的处理等.
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