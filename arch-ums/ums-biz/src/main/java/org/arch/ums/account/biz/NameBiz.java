package org.arch.ums.account.biz;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudBiz;
import org.arch.framework.ums.bean.TokenInfo;
import org.arch.ums.account.dto.NameRequest;
import org.arch.ums.account.dto.NameSearchDto;
import org.arch.ums.account.entity.Name;
import org.arch.ums.account.rest.NameRest;
import org.arch.ums.account.service.NameService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import top.dcenter.ums.security.core.api.tenant.handler.TenantContextHolder;

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

}
