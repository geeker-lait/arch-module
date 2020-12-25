package com.uni.skit.user.controller;

import com.uni.common.constant.BizIdCode;
import com.uni.common.entity.TokenInfo;
import com.uni.common.service.IdService;
import com.uni.common.utils.RedisUtils;
import com.uni.common.utils.SecurityUtils;
import com.uni.common.utils.StringUtils;
import com.uni.pay.channel.sharelink.domain.ShareLinkPayResponse;
import com.uni.pay.core.*;
import com.uni.skit.user.biz.IOrderBiz;
import com.uni.skit.user.dto.OrderDto;
import com.uni.skit.user.vo.ApiBaseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

    @Autowired
    private PayRouting payRouting;

    @Autowired
    private IOrderBiz orderBiz;
    @Autowired
    private IdService idService;
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private Environment env;

    /**
     * 绑卡
     *
     * @param httpServletRequest
     * @param httpServletResponse
     */
    @PostMapping(value = "preBindcard")
    @ResponseBody
    @ApiOperation(value = "绑卡发送验证码", authorizations = @Authorization(value = "token"))
    public ApiBaseResult preBindcard(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        PayRequest payRequest = PayRequest.of(DirectiveName.preBindCardDirective).init(httpServletRequest);
        TokenInfo token = SecurityUtils.getCurrentUser();
        initPayRequest(payRequest, token, BizIdCode.PAY_BINDCARD_ID);
        PayResponse payResponse = payRouting.routing(payRequest);
        return ApiBaseResult.success(payResponse);
    }

    /**
     * 绑卡校验
     *
     * @param httpServletRequest
     * @param httpServletResponse
     */
    @PostMapping(value = "bindcard")
    @ResponseBody
    @ApiOperation(value = "确认绑卡", authorizations = @Authorization(value = "token"))
    public ApiBaseResult bindcard(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        PayRequest payRequest = PayRequest.of(DirectiveName.bindCardDirective).init(httpServletRequest);
        TokenInfo token = SecurityUtils.getCurrentUser();
        initPayRequest(payRequest, token, BizIdCode.PAY_BINDCARD_ID);
        PayResponse payResponse = payRouting.routing(payRequest);
        return ApiBaseResult.success(payResponse);
    }


    /**
     * 预付
     *
     * @param httpServletRequest
     * @param httpServletResponse
     */
    @PostMapping(value = "prePay")
    @ResponseBody
    @ApiOperation(value = "预付", authorizations = @Authorization(value = "token"))
//    @Transactional(rollbackFor = Exception.class)
    public ApiBaseResult prePay(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        PayRequest payRequest = PayRequest.of(DirectiveName.prePaymentDirective).init(httpServletRequest);
        TokenInfo tokenInfo = SecurityUtils.getCurrentUser();
        String amount = redisUtils.getStr("vip88.productAmount");
        if (StringUtils.isEmpty(amount)) {
            amount = env.getProperty("vip88.productAmount");
        }
        payRequest.setAmount(amount);
        initPayRequest(payRequest, tokenInfo, BizIdCode.PAY_WITHHOLD_ID);

        //创建订单
        OrderDto orderDto = new OrderDto();
        orderDto.setAppId(tokenInfo.getAppId());
        orderDto.setProductId(env.getProperty("vip.productId"));
        orderDto.setPaymentId(payRequest.getPaymentId());
        orderBiz.addOrder(orderDto);

        //支付
        PayResponse payResponse = payRouting.routing(payRequest);
        //update订单
        updateOrder(payResponse);
        return ApiBaseResult.success(payResponse);
    }

    /**
     * 转账
     *
     * @param httpServletRequest
     * @param httpServletResponse
     */
    @PostMapping(value = "pay")
    @ResponseBody
    @ApiOperation(value = "代付", authorizations = @Authorization(value = "token"))
//    @Transactional(rollbackFor = Exception.class)
    public ApiBaseResult transferDirective(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        PayRequest payRequest = PayRequest.of(DirectiveName.paymentDirective).init(httpServletRequest);
        TokenInfo tokenInfo = SecurityUtils.getCurrentUser();
        String amount = redisUtils.getStr("vip88.productAmount");
        if (StringUtils.isEmpty(amount)) {
            amount = env.getProperty("vip88.productAmount");
        }
        payRequest.setAmount(amount);
        initPayRequest(payRequest, tokenInfo, BizIdCode.PAY_FOR_ANOTHER_ID);
        String payOrderIdKey = tokenInfo.getAccountId() + "_prepay_payOrderId";
        String paymentId = redisUtils.getStr(payOrderIdKey);
        payRequest.setPaymentId(paymentId);
        PayResponse<ShareLinkPayResponse> payResponse = payRouting.routing(payRequest);
        //update订单
        updateOrder(payResponse);
        return ApiBaseResult.success(payResponse);
    }



    private void initPayRequest(PayRequest payRequest, TokenInfo token, BizIdCode bizIdCode) {
        if (token != null) {
            payRequest.setAccountId(token.getAccountId());
            payRequest.setAppId(token.getAppId());
            payRequest.setUserId(token.getUserId());
//            String key = RedisKeyCode.PAY_PAYMENTID + token.getAppId()+token.getAccountId();
//            String paymentId = redisUtils.getStr(key);
//            if (StringUtils.isEmpty(paymentId)) {
//                paymentId = idService.generateId(bizIdCode);
//                redisUtils.set(key, paymentId, 60 * 60 * 2);
//            }
//            String payOrderIdKey = token.getAccountId() + "_prepay_payOrderId";
//            String paymentId = redisUtils.getStr(payOrderIdKey);
//            if (StringUtils.isEmpty(paymentId)) {
//                paymentId = idService.generateId(bizIdCode);
//            }
            String paymentId = idService.generateId(bizIdCode);
            payRequest.setPaymentId(paymentId);
        }
    }

    private void updateOrder(PayResponse payResponse) {
        OrderDto orderDto = new OrderDto();
        orderDto.setPaymentId(payResponse.getPaymentId());
        if (!"1000000".equals(payResponse.getCode())) {
            //更新订单失败
            orderDto.setOrderStatus("fail");
        }else{
            if (payResponse != null && 0 == payResponse.getNeedSms()) {
                //支付中
                orderDto.setOrderStatus("padding");
            }else{
                //支付成功
                orderDto.setOrderStatus("success");
            }
        }
        orderBiz.updateOrder(orderDto,payResponse);
    }

}
