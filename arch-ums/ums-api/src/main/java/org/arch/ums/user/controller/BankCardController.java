package org.arch.ums.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudController;
import org.arch.framework.ums.bean.TokenInfo;
import org.arch.ums.user.dto.BankCardSearchDto;
import org.arch.ums.user.entity.BankCard;
import org.arch.ums.user.service.BankCardService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.dcenter.ums.security.core.api.tenant.handler.TenantContextHolder;

import static java.util.Objects.nonNull;

/**
 * 用户银行卡信息(BankCard) 表服务控制器
 *
 * @author YongWu zheng
 * @date 2021-02-26 23:19:51
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/user/bank/card")
public class BankCardController implements CrudController<BankCard, java.lang.Long, BankCardSearchDto, BankCardService> {

    private final TenantContextHolder tenantContextHolder;
    private final BankCardService bankCardService;

    @Override
    public BankCard resolver(TokenInfo token, BankCard bankCard) {
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
