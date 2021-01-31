package org.arch.ums.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudController;
import org.arch.framework.ums.bean.TokenInfo;
import org.arch.framework.ums.properties.AppProperties;
import org.arch.ums.user.dto.BankCardSearchDto;
import org.arch.ums.user.entity.BankCard;
import org.arch.ums.user.service.BankCardService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static java.util.Objects.nonNull;

/**
 * 用户银行卡信息(BankCard) 表服务控制器
 *
 * @author YongWu zheng
 * @date 2021-01-30 11:31:33
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/user/bank/card")
public class BankCardController implements CrudController<BankCard, Long, BankCardSearchDto, BankCardService> {

    private final AppProperties appProperties;
    private final BankCardService bankCardService;

    @Override
    public BankCard resolver(TokenInfo token, BankCard bankCard) {
        // TODO 默认实现不处理, 根据 TokenInfo 处理 bankCard 后返回 bankCard, 如: tenantId 的处理等.
        if (nonNull(token) && nonNull(token.getTenantId())) {
            bankCard.setTenantId(token.getTenantId());
        }
        else {
            bankCard.setTenantId(appProperties.getSystemTenantId());
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