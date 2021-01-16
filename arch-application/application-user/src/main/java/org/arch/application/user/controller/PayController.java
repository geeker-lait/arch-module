package org.arch.application.user.controller;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * GrantDirective,
 * LoanDirective,
 * QueryDirective,
 * RefundDirective,
 * RepaymentDirective,
 * SignDirective,
 * BindCardDirective,
 * TransferDirective,
 * BankcardBindVerify,
 * WithholdDirective
 */
@RestController
@RequestMapping("pay")
@Slf4j
@Api(tags = "用户绑卡支付相关")
public class PayController {


}
