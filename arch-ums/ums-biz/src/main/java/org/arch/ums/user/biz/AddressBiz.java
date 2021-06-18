package org.arch.ums.user.biz;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudBiz;
import org.arch.framework.ums.bean.TokenInfo;
import org.arch.ums.user.dto.AddressRequest;
import org.arch.ums.user.dto.AddressSearchDto;
import org.arch.ums.user.entity.Address;
import org.arch.ums.user.rest.AddressRest;
import org.arch.ums.user.service.AddressService;
import org.springframework.beans.BeanUtils;
import top.dcenter.ums.security.core.api.tenant.handler.TenantContextHolder;

import static java.util.Objects.nonNull;

/**
 * 用户地址表(Address) 表 Biz 服务
 *
 * @author zheng
 * @date 2021-06-06 18:53:15
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
public class AddressBiz implements CrudBiz<AddressRequest, Address, java.lang.Long, AddressSearchDto, AddressSearchDto, AddressService>, AddressRest {

    private final TenantContextHolder tenantContextHolder;
    private final AddressService addressService;

    @Override
    public Address resolver(TokenInfo token, AddressRequest request) {
        Address address = new Address();
        if (nonNull(request)) {
            BeanUtils.copyProperties(request, address);
        }
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
