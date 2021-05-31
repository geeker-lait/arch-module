package org.arch.payment.server.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.ums.bean.TokenInfo;
import org.arch.payment.core.PayDispatcher;
import org.arch.payment.sdk.DirectiveCode;
import org.arch.payment.sdk.PayRequest;
import org.arch.payment.sdk.PayResponse;
import org.arch.payment.sdk.params.BindCardParams;
import org.arch.payment.sdk.params.PayingParams;
import org.arch.payment.sdk.params.PreBindCardParams;
import org.arch.payment.sdk.params.PrePayingParams;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author lait.zhang@gmail.com
 * @description: 支付相关接口
 * @weixin PN15855012581
 * @date 1/14/2021 2:01 PM
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("pay")
@Slf4j
@Api(tags = "支付相关")
public class PayController {

    private final PayDispatcher payDispatcher;
    private final Environment env;

    /**
     * 绑卡验证
     *
     * @param httpServletRequest
     * @param preBindCardParams
     * @return
     */
    @PostMapping(value = "preBindCard")
    @ResponseBody
    @ApiOperation(value = "绑卡验证", authorizations = @Authorization(value = "token"))
    public PayResponse preBindcard(HttpServletRequest httpServletRequest, TokenInfo token, PreBindCardParams preBindCardParams) {
        PayRequest payRequest = PayRequest.of(DirectiveCode.PRE_BINDCARD_DIRECTIVE)
                .init(httpServletRequest)
                .setPayParams(preBindCardParams);


        payDispatcher.dispatch(payRequest);
        return null;
    }

    /**
     * 确认绑卡
     *
     * @param httpServletRequest
     * @param token
     * @param bindCardParams
     * @return
     */
    @PostMapping(value = "bindCard")
    @ResponseBody
    @ApiOperation(value = "确认绑卡", authorizations = @Authorization(value = "token"))
    public PayResponse bindcard(HttpServletRequest httpServletRequest, TokenInfo token, BindCardParams bindCardParams) {
        PayRequest payRequest = PayRequest.of(DirectiveCode.BINDCARD_DIRECTIVE).init(httpServletRequest).setPayParams(bindCardParams);
        // 获取token

        // 获取ip地址
        //String ip = httpServletRequest.getIp();
        payDispatcher.dispatch(payRequest);
        return null;
    }


    /**
     * 预付
     *
     * @param httpServletRequest
     * @param prePayingParams
     */
    @PostMapping(value = "prePay")
    @ResponseBody
    @ApiOperation(value = "支付验证/预支付", authorizations = @Authorization(value = "token"))
    public PayResponse prePaying(HttpServletRequest httpServletRequest, TokenInfo token, PrePayingParams prePayingParams) {
        PayRequest payRequest = PayRequest.of(DirectiveCode.PRE_PAY_DIRECTIVE).init(httpServletRequest).setPayParams(prePayingParams);
        payDispatcher.dispatch(payRequest);
        return null;
    }

    /**
     * 转账
     *
     * @param httpServletRequest
     * @param token
     */
    @PostMapping(value = "pay")
    @ResponseBody
    @ApiOperation(value = "支付确认/支付", authorizations = @Authorization(value = "token"))
//    @Transactional(rollbackFor = Exception.class)
    public PayResponse paying(HttpServletRequest httpServletRequest, TokenInfo token, PayingParams payingParams) {
        PayRequest payRequest = PayRequest.of(DirectiveCode.PAY_DIRECTIVE)
                .setPayParams(payingParams);
        payDispatcher.dispatch(payRequest);
        return null;
    }


}
