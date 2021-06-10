package org.arch.ums.conf.biz;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudBiz;
import org.arch.framework.ums.bean.TokenInfo;
import org.arch.ums.conf.dto.DictionaryRequest;
import org.arch.ums.conf.dto.DictionarySearchDto;
import org.arch.ums.conf.entity.Dictionary;
import org.arch.ums.conf.rest.DictionaryRest;
import org.arch.ums.conf.service.DictionaryService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import top.dcenter.ums.security.core.api.tenant.handler.TenantContextHolder;

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

}
