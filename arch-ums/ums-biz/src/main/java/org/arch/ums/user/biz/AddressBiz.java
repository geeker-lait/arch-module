package org.arch.ums.user.biz;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudBiz;
import org.arch.framework.ums.bean.TokenInfo;
import org.arch.framework.utils.SecurityUtils;
import org.arch.ums.user.dto.AddressRequest;
import org.arch.ums.user.dto.AddressSearchDto;
import org.arch.ums.user.entity.Address;
import org.arch.ums.user.rest.AddressRest;
import org.arch.ums.user.service.AddressService;
import org.springframework.beans.BeanUtils;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;
import top.dcenter.ums.security.core.api.tenant.handler.TenantContextHolder;

import java.util.List;
import java.util.stream.Collectors;

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

    /**
     * 根据 entity 条件查询对象.
     * 注意: 此 API 适合 Feign 远程调用 或 HttpClient 包 json 字符串放入 body 也行.
     *
     * @param request 实体的 request 类型
     * @return DTO
     */
    @Override
    @NonNull
    @Transactional(readOnly = true)
    public AddressSearchDto findOne(AddressRequest request) {
        TokenInfo token = SecurityUtils.getCurrentUser();
        Address address = resolver(token, request);
        AddressSearchDto searchDto = convertSearchDto(address);
        Address result = getCrudService().findOneByMapParams(searchDto.searchParams());
        return convertReturnDto(result);
    }

    /**
     * 根据 entity 条件查询对象列表.
     * 注意: 此 API 适合 Feign 远程调用 或 HttpClient 包 json 字符串放入 body 也行.
     *
     * @param request 实体的 request 类型
     * @return DTO List
     */
    @Override
    @NonNull
    @Transactional(readOnly = true)
    public List<AddressSearchDto> find(AddressRequest request) {
        TokenInfo token = SecurityUtils.getCurrentUser();
        Address address = resolver(token, request);
        AddressSearchDto searchDto = convertSearchDto(address);
        List<Address> addressList = getCrudService().findAllByMapParams(searchDto.searchParams());
        return addressList.stream().map(this::convertReturnDto).collect(Collectors.toList());
    }

    /**
     * 分页查询.
     * 注意: 此 API 适合 Feign 远程调用 或 HttpClient 包 json 字符串放入 body 也行.
     *
     * @param request    实体的 request 类型
     * @param pageNumber 第几页
     * @param pageSize   页大小
     * @return {@link IPage}
     */
    @Override
    @NonNull
    @Transactional(readOnly = true)
    public IPage<AddressSearchDto> page(AddressRequest request, Integer pageNumber, Integer pageSize) {
        TokenInfo token = SecurityUtils.getCurrentUser();
        Address address = resolver(token, request);
        AddressSearchDto searchDto = convertSearchDto(address);
        IPage<Address> page = getCrudService().findPage(searchDto.searchParams(), pageNumber, pageSize);
        return page.convert(this::convertReturnDto);
    }

}
