package org.arch.ums.conf.biz;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudBiz;
import org.arch.framework.ums.bean.TokenInfo;
import org.arch.ums.conf.dto.DictionaryItemRequest;
import org.arch.ums.conf.dto.DictionaryItemSearchDto;
import org.arch.ums.conf.entity.DictionaryItem;
import org.arch.ums.conf.rest.DictionaryItemRest;
import org.arch.ums.conf.service.DictionaryItemService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import top.dcenter.ums.security.core.api.tenant.handler.TenantContextHolder;

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

}
