package org.arch.payment.server.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.utils.HttpRequestUtils;
import org.arch.payment.core.PayDispatcher;
import org.arch.payment.sdk.DirectiveCode;
import org.arch.payment.sdk.PayRequest;
import org.arch.payment.sdk.PayResponse;
import org.arch.payment.sdk.request.BindCardRequest;
import org.arch.payment.sdk.request.PayingRequest;
import org.arch.payment.sdk.request.PreBindCardRequest;
import org.arch.payment.sdk.request.PrePayingRequest;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 支付相关接口
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
     * @param preBindCardRequest
     * @return
     */
    @PostMapping(value = "preBindCard")
    @ResponseBody
    @ApiOperation(value = "绑卡验证", authorizations = @Authorization(value = "token"))
    public PayResponse preBindcard(HttpServletRequest httpServletRequest, TokenInfo tokenInfo, PreBindCardRequest preBindCardRequest) {
        PayRequest payRequest = PayRequest.of(DirectiveCode.PRE_BINDCARD_DIRECTIVE)
                .setIp(HttpRequestUtils.getIpAddress(httpServletRequest))
                .setDeviceId(HttpRequestUtils.getDeviceId(httpServletRequest))
                .init(preBindCardRequest);

        payDispatcher.dispatch(payRequest);
        return null;
    }

    /**
     * 确认绑卡
     *
     * @param httpServletRequest
     * @param tokenInfo
     * @param bindCardRequest
     * @return
     */
    @PostMapping(value = "bindCard")
    @ResponseBody
    @ApiOperation(value = "确认绑卡", authorizations = @Authorization(value = "token"))
    public PayResponse bindcard(HttpServletRequest httpServletRequest, TokenInfo tokenInfo, BindCardRequest bindCardRequest) {
        PayRequest payRequest = PayRequest.of(DirectiveCode.BINDCARD_DIRECTIVE);
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
     * @param prePayingRequest
     */
    @PostMapping(value = "prePay")
    @ResponseBody
    @ApiOperation(value = "支付验证/预支付", authorizations = @Authorization(value = "token"))
    public PayResponse prePaying(HttpServletRequest httpServletRequest, TokenInfo tokenInfo, PrePayingRequest prePayingRequest) {
        PayRequest payRequest = PayRequest.of(DirectiveCode.PRE_PAY_DIRECTIVE).init(prePayingRequest);
        payDispatcher.dispatch(payRequest);
        return null;
    }

    /**
     * 转账
     *
     * @param httpServletRequest
     * @param tokenInfo
     */
    @PostMapping(value = "pay")
    @ResponseBody
    @ApiOperation(value = "支付确认/支付", authorizations = @Authorization(value = "token"))
//    @Transactional(rollbackFor = Exception.class)
    public PayResponse paying(HttpServletRequest httpServletRequest, TokenInfo tokenInfo, PayingRequest payingRequest) {
        PayRequest payRequest = PayRequest.of(DirectiveCode.PAY_DIRECTIVE)
                .init(payingRequest);
        payDispatcher.dispatch(payRequest);
        return null;
    }


}
