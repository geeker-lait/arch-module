package org.arch.ums.conf.biz;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudBiz;
import org.arch.framework.ums.bean.TokenInfo;
import org.arch.framework.utils.SecurityUtils;
import org.arch.ums.conf.dto.DictionaryItemRequest;
import org.arch.ums.conf.dto.DictionaryItemSearchDto;
import org.arch.ums.conf.entity.DictionaryItem;
import org.arch.ums.conf.rest.DictionaryItemRest;
import org.arch.ums.conf.service.DictionaryItemService;
import org.springframework.beans.BeanUtils;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.dcenter.ums.security.core.api.tenant.handler.TenantContextHolder;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

/**
 * 数据字典明细(DictionaryItem) 表 Biz 服务
 *
 * @author zheng
 * @date 2021-06-06 18:52:31
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class DictionaryItemBiz implements CrudBiz<DictionaryItemRequest, DictionaryItem, java.lang.Long, DictionaryItemSearchDto, DictionaryItemSearchDto, DictionaryItemService>, DictionaryItemRest {

    private final TenantContextHolder tenantContextHolder;
    private final DictionaryItemService dictionaryItemService;

    @Override
    public DictionaryItem resolver(TokenInfo token, DictionaryItemRequest request) {
        DictionaryItem dictionaryItem = new DictionaryItem();
        if (nonNull(request)) {
            BeanUtils.copyProperties(request, dictionaryItem);
        }
        if (nonNull(token) && nonNull(token.getTenantId())) {
            dictionaryItem.setTenantId(token.getTenantId());
        }
        else {
            dictionaryItem.setTenantId(Integer.parseInt(tenantContextHolder.getTenantId()));
        }
        return dictionaryItem;
    }

    @Override
    public DictionaryItemService getCrudService() {
        return dictionaryItemService;
    }

    @Override
    public DictionaryItemSearchDto getSearchDto() {
        return new DictionaryItemSearchDto();
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
    public DictionaryItemSearchDto findOne(DictionaryItemRequest request) {
        TokenInfo token = SecurityUtils.getCurrentUser();
        DictionaryItem dictionaryItem = resolver(token, request);
        DictionaryItemSearchDto searchDto = convertSearchDto(dictionaryItem);
        DictionaryItem result = getCrudService().findOneByMapParams(searchDto.searchParams());
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
    public List<DictionaryItemSearchDto> find(DictionaryItemRequest request) {
        TokenInfo token = SecurityUtils.getCurrentUser();
        DictionaryItem dictionaryItem = resolver(token, request);
        DictionaryItemSearchDto searchDto = convertSearchDto(dictionaryItem);
        List<DictionaryItem> dictionaryItemList = getCrudService().findAllByMapParams(searchDto.searchParams());
        return dictionaryItemList.stream().map(this::convertReturnDto).collect(Collectors.toList());
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
    public IPage<DictionaryItemSearchDto> page(DictionaryItemRequest request, Integer pageNumber, Integer pageSize) {
        TokenInfo token = SecurityUtils.getCurrentUser();
        DictionaryItem dictionaryItem = resolver(token, request);
        DictionaryItemSearchDto searchDto = convertSearchDto(dictionaryItem);
        IPage<DictionaryItem> page = getCrudService().findPage(searchDto.searchParams(), pageNumber, pageSize);
        return page.convert(this::convertReturnDto);
    }

}
