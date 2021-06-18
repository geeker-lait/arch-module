package org.arch.ums.user.biz;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudBiz;
import org.arch.framework.ums.bean.TokenInfo;
import org.arch.ums.user.dto.IdCardRequest;
import org.arch.ums.user.dto.IdCardSearchDto;
import org.arch.ums.user.entity.IdCard;
import org.arch.ums.user.rest.IdCardRest;
import org.arch.ums.user.service.IdCardService;
import org.springframework.beans.BeanUtils;
import top.dcenter.ums.security.core.api.tenant.handler.TenantContextHolder;

import static java.util.Objects.nonNull;

/**
 * 用户身份证表(IdCard) 表 Biz 服务
 *
 * @author zheng
 * @date 2021-06-06 18:53:15
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
public class IdCardBiz implements CrudBiz<IdCardRequest, IdCard, java.lang.Long, IdCardSearchDto, IdCardSearchDto, IdCardService>, IdCardRest {

    private final TenantContextHolder tenantContextHolder;
    private final IdCardService idCardService;

    @Override
    public IdCard resolver(TokenInfo token, IdCardRequest request) {
        IdCard idCard = new IdCard();
        if (nonNull(request)) {
            BeanUtils.copyProperties(request, idCard);
        }
        if (nonNull(token) && nonNull(token.getTenantId())) {
            idCard.setTenantId(token.getTenantId());
        }
        else {
            idCard.setTenantId(Integer.parseInt(tenantContextHolder.getTenantId()));
        }
        return idCard;
    }

    @Override
    public IdCardService getCrudService() {
        return idCardService;
    }

    @Override
    public IdCardSearchDto getSearchDto() {
        return new IdCardSearchDto();
    }

}
