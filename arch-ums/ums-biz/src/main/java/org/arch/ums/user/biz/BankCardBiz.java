package org.arch.ums.user.biz;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudBiz;
import org.arch.framework.ums.bean.TokenInfo;
import org.arch.ums.user.dto.BankCardRequest;
import org.arch.ums.user.dto.BankCardSearchDto;
import org.arch.ums.user.entity.BankCard;
import org.arch.ums.user.service.BankCardService;
import org.arch.ums.user.rest.BankCardRest;
import org.springframework.beans.BeanUtils;
import top.dcenter.ums.security.core.api.tenant.handler.TenantContextHolder;

import static java.util.Objects.nonNull;

/**
 * 用户银行卡信息(BankCard) 表 Biz 服务
 *
 * @author zheng
 * @date 2021-06-06 18:53:15
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
public class BankCardBiz implements CrudBiz<BankCardRequest, BankCard, java.lang.Long, BankCardSearchDto, BankCardSearchDto, BankCardService>, BankCardRest {

    private final TenantContextHolder tenantContextHolder;
    private final BankCardService bankCardService;

    @Override
    public BankCard resolver(TokenInfo token, BankCardRequest request) {
        BankCard bankCard = new BankCard();
        if (nonNull(request)) {
            BeanUtils.copyProperties(request, bankCard);
        }
        if (nonNull(token) && nonNull(token.getTenantId())) {
            bankCard.setTenantId(token.getTenantId());
        }
        else {
            bankCard.setTenantId(Integer.parseInt(tenantContextHolder.getTenantId()));
        }
        return bankCard;
    }

    @Override
    public BankCardService getCrudService() {
        return bankCardService;
    }

    @Override
    public BankCardSearchDto getSearchDto() {
        return new BankCardSearchDto();
    }

}
