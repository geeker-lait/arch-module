package org.arch.application.user.controller;

import org.arch.ums.account.biz.IBankCardBiz;
import org.arch.ums.account.dto.BankCardDelDto;
import org.arch.ums.account.vo.ApiBaseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Description: 银行卡信息
 *
 * @author kenzhao
 * @date 2020/4/14 17:37
 */
@Api(tags = "银行卡信息")
@RestController
public class BankCardController {

    @Autowired
    private IBankCardBiz bankCardBiz;

    @ApiOperation(value = "获取已绑卡列表", authorizations = @Authorization(value = "token"))
    @PostMapping("/bankcard/getBankCards")
    public ApiBaseResult getBankCards(HttpServletRequest request) {
        return bankCardBiz.getBankCards();
    }
    @ApiOperation(value = "删除指定的已绑卡", authorizations = @Authorization(value = "token"))
    @PostMapping("/bankcard/delBankCard")
    public ApiBaseResult delBankCard(@Validated @RequestBody BankCardDelDto bankCardDelDto, HttpServletRequest request) {
        return bankCardBiz.delBankCard(bankCardDelDto.getBankCard());
    }
}