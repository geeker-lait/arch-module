package org.arch.ums.user.biz;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudBiz;
import org.arch.framework.ums.bean.TokenInfo;
import org.arch.framework.utils.SecurityUtils;
import org.arch.ums.user.dto.PhoneRequest;
import org.arch.ums.user.dto.PhoneSearchDto;
import org.arch.ums.user.entity.Phone;
import org.arch.ums.user.service.PhoneService;
import org.arch.ums.user.rest.PhoneRest;
import org.springframework.beans.BeanUtils;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;
import top.dcenter.ums.security.core.api.tenant.handler.TenantContextHolder;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/**
 * 用户电话信息(Phone) 表 Biz 服务
 *
 * @author zheng
 * @date 2021-06-06 18:53:16
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
public class PhoneBiz implements CrudBiz<PhoneRequest, Phone, java.lang.Long, PhoneSearchDto, PhoneSearchDto, PhoneService>, PhoneRest {

    private final TenantContextHolder tenantContextHolder;
    private final PhoneService phoneService;

    @Override
    public Phone resolver(TokenInfo token, PhoneRequest request) {
        Phone phone = new Phone();
        if (nonNull(request)) {
            BeanUtils.copyProperties(request, phone);
        }
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
    public PhoneSearchDto findOne(PhoneRequest request) {
        TokenInfo token = SecurityUtils.getCurrentUser();
        Phone phone = resolver(token, request);
        PhoneSearchDto searchDto = convertSearchDto(phone);
        Phone result = getCrudService().findOneByMapParams(searchDto.searchParams());
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
    public List<PhoneSearchDto> find(PhoneRequest request) {
        TokenInfo token = SecurityUtils.getCurrentUser();
        Phone phone = resolver(token, request);
        PhoneSearchDto searchDto = convertSearchDto(phone);
        List<Phone> phoneList = getCrudService().findAllByMapParams(searchDto.searchParams());
        return phoneList.stream().map(this::convertReturnDto).collect(Collectors.toList());
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
    public IPage<PhoneSearchDto> page(PhoneRequest request, Integer pageNumber, Integer pageSize) {
        TokenInfo token = SecurityUtils.getCurrentUser();
        Phone phone = resolver(token, request);
        PhoneSearchDto searchDto = convertSearchDto(phone);
        IPage<Phone> page = getCrudService().findPage(searchDto.searchParams(), pageNumber, pageSize);
        return page.convert(this::convertReturnDto);
    }

}
