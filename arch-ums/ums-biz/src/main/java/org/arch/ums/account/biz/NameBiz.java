package org.arch.ums.account.biz;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudBiz;
import org.arch.framework.ums.bean.TokenInfo;
import org.arch.framework.utils.SecurityUtils;
import org.arch.ums.account.dto.NameRequest;
import org.arch.ums.account.dto.NameSearchDto;
import org.arch.ums.account.entity.Name;
import org.arch.ums.account.rest.NameRest;
import org.arch.ums.account.service.NameService;
import org.springframework.beans.BeanUtils;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.dcenter.ums.security.core.api.tenant.handler.TenantContextHolder;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

/**
 * 账号名(Name) 表 Biz 服务
 *
 * @author zheng
 * @date 2021-06-06 18:51:03
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class NameBiz implements CrudBiz<NameRequest, Name, java.lang.Long, NameSearchDto, NameSearchDto, NameService>, NameRest {

    private final TenantContextHolder tenantContextHolder;
    private final NameService nameService;

    @Override
    public Name resolver(TokenInfo token, NameRequest request) {
        Name name = new Name();
        if (nonNull(request)) {
            BeanUtils.copyProperties(request, name);
        }
        if (nonNull(token) && nonNull(token.getTenantId())) {
            name.setTenantId(token.getTenantId());
        }
        else {
            name.setTenantId(Integer.parseInt(tenantContextHolder.getTenantId()));
        }
        return name;
    }

    @Override
    public NameService getCrudService() {
        return nameService;
    }

    @Override
    public NameSearchDto getSearchDto() {
        return new NameSearchDto();
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
    public NameSearchDto findOne(NameRequest request) {
        TokenInfo token = SecurityUtils.getCurrentUser();
        Name name = resolver(token, request);
        NameSearchDto searchDto = convertSearchDto(name);
        Name result = getCrudService().findOneByMapParams(searchDto.searchParams());
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
    public List<NameSearchDto> find(NameRequest request) {
        TokenInfo token = SecurityUtils.getCurrentUser();
        Name name = resolver(token, request);
        NameSearchDto searchDto = convertSearchDto(name);
        List<Name> nameList = getCrudService().findAllByMapParams(searchDto.searchParams());
        return nameList.stream().map(this::convertReturnDto).collect(Collectors.toList());
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
    public IPage<NameSearchDto> page(NameRequest request, Integer pageNumber, Integer pageSize) {
        TokenInfo token = SecurityUtils.getCurrentUser();
        Name name = resolver(token, request);
        NameSearchDto searchDto = convertSearchDto(name);
        IPage<Name> page = getCrudService().findPage(searchDto.searchParams(), pageNumber, pageSize);
        return page.convert(this::convertReturnDto);
    }

}
