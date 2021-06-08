package org.arch.ums.conf.biz;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudBiz;
import org.arch.framework.ums.bean.TokenInfo;
import org.arch.framework.utils.SecurityUtils;
import org.arch.ums.conf.dto.DictionaryRequest;
import org.arch.ums.conf.dto.DictionarySearchDto;
import org.arch.ums.conf.entity.Dictionary;
import org.arch.ums.conf.rest.DictionaryRest;
import org.arch.ums.conf.service.DictionaryService;
import org.springframework.beans.BeanUtils;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.dcenter.ums.security.core.api.tenant.handler.TenantContextHolder;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

/**
 * 数据字典(Dictionary) 表 Biz 服务
 *
 * @author zheng
 * @date 2021-06-06 18:52:31
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class DictionaryBiz implements CrudBiz<DictionaryRequest, Dictionary, java.lang.Long, DictionarySearchDto, DictionarySearchDto, DictionaryService>, DictionaryRest {

    private final TenantContextHolder tenantContextHolder;
    private final DictionaryService dictionaryService;

    @Override
    public Dictionary resolver(TokenInfo token, DictionaryRequest request) {
        Dictionary dictionary = new Dictionary();
        if (nonNull(request)) {
            BeanUtils.copyProperties(request, dictionary);
        }
        if (nonNull(token) && nonNull(token.getTenantId())) {
            dictionary.setTenantId(token.getTenantId());
        }
        else {
            dictionary.setTenantId(Integer.parseInt(tenantContextHolder.getTenantId()));
        }
        return dictionary;
    }

    @Override
    public DictionaryService getCrudService() {
        return dictionaryService;
    }

    @Override
    public DictionarySearchDto getSearchDto() {
        return new DictionarySearchDto();
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
    public DictionarySearchDto findOne(DictionaryRequest request) {
        TokenInfo token = SecurityUtils.getCurrentUser();
        Dictionary dictionary = resolver(token, request);
        DictionarySearchDto searchDto = convertSearchDto(dictionary);
        Dictionary result = getCrudService().findOneByMapParams(searchDto.searchParams());
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
    public List<DictionarySearchDto> find(DictionaryRequest request) {
        TokenInfo token = SecurityUtils.getCurrentUser();
        Dictionary dictionary = resolver(token, request);
        DictionarySearchDto searchDto = convertSearchDto(dictionary);
        List<Dictionary> dictionaryList = getCrudService().findAllByMapParams(searchDto.searchParams());
        return dictionaryList.stream().map(this::convertReturnDto).collect(Collectors.toList());
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
    public IPage<DictionarySearchDto> page(DictionaryRequest request, Integer pageNumber, Integer pageSize) {
        TokenInfo token = SecurityUtils.getCurrentUser();
        Dictionary dictionary = resolver(token, request);
        DictionarySearchDto searchDto = convertSearchDto(dictionary);
        IPage<Dictionary> page = getCrudService().findPage(searchDto.searchParams(), pageNumber, pageSize);
        return page.convert(this::convertReturnDto);
    }

}
