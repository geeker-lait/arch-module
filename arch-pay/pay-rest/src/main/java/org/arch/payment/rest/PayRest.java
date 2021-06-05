package org.arch.payment.rest;

import org.arch.payment.api.PayChannelAccountDto;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;


/**
 * 发起支付入口
 */
@RestController
@RequestMapping
public interface PayRest {

    @RequestMapping("/")
    default ModelAndView index() {
        return new ModelAndView("/index.html");
    }


    /**
     * 增加支付商户
     *
     * @param payChannelAccountDto 支付账户信息
     * @return 支付账户信息
     */
    @RequestMapping("addMerchantChannel")
    Map<String, Object> add(PayChannelAccountDto payChannelAccountDto);


    /**
     * 跳到支付页面
     * 针对实时支付,即时付款
     *
     * @param request         请求
     * @param payId           账户id
     * @param transactionType 交易类型， 这个针对于每一个 支付类型的对应的几种交易方式
     * @param bankType        针对刷卡支付，卡的类型，类型值
     * @param price           金额
     * @return 跳到支付页面
     */
    @RequestMapping(value = "toPay.html", produces = "text/html;charset=UTF-8")
    String toPay(HttpServletRequest request, Integer payId, String transactionType, String bankType, BigDecimal price);

    /**
     * 跳到支付页面
     * 针对实时支付,即时付款
     *
     * @param request 请求
     * @return 跳到支付页面
     */
    @RequestMapping(value = "toWxPay.html", produces = "text/html;charset=UTF-8")
    String toWxPay(HttpServletRequest request);


    /**
     * 公众号支付
     *
     * @param payId  账户id
     * @param openid openid
     * @param price  金额
     * @return 返回jsapi所需参数
     */
    @RequestMapping(value = "jsapi")
    Map toPay(Integer payId, String openid, BigDecimal price);

    /**
     * 获取支付预订单信息
     *
     * @param payId           支付账户id
     * @param transactionType 交易类型
     * @param price           金额
     * @return 支付预订单信息
     */
    @RequestMapping("app")
    Map<String, Object> getOrderInfo(Integer payId, String transactionType, BigDecimal price);

    /**
     * 刷卡付,pos主动扫码付款(条码付)
     *
     * @param payId           账户id
     * @param transactionType 交易类型， 这个针对于每一个 支付类型的对应的几种交易方式
     * @param authCode        授权码，条码等
     * @param price           金额
     * @return 支付结果
     */
    @RequestMapping(value = "microPay")
    Map<String, Object> microPay(Integer payId, String transactionType, BigDecimal price, String authCode);

    /**
     * 获取二维码图像
     * 二维码支付
     *
     * @param payId           账户id
     * @param transactionType 交易类型， 这个针对于每一个 支付类型的对应的几种交易方式
     * @param price           金额
     * @return 二维码图像
     * @throws IOException IOException
     */
    @RequestMapping(value = "toQrPay.jpg", produces = "image/jpeg;charset=UTF-8")
    byte[] toWxQrPay(Integer payId, String transactionType, BigDecimal price) throws IOException;

    /**
     * 获取二维码地址
     * 二维码支付
     *
     * @param price 金额
     * @return 二维码图像
     * @throws IOException IOException
     */
    @RequestMapping(value = "getQrPay.json")
    String getQrPay(Integer payId, String transactionType, BigDecimal price) throws IOException;

    /**
     * 获取一码付二维码图像
     * 二维码支付
     *
     * @param wxPayId  微信账户id
     * @param aliPayId 支付宝id
     * @param price    金额
     * @param request  请求
     * @return 二维码图像
     * @throws IOException IOException
     */
    @RequestMapping(value = "toWxAliQrPay.jpg", produces = "image/jpeg;charset=UTF-8")
    byte[] toWxAliQrPay(Integer wxPayId, Integer aliPayId, BigDecimal price, HttpServletRequest request) throws IOException;

    /**
     * 支付宝与微信平台的判断 并进行支付的转跳
     *
     * @param wxPayId  微信账户id
     * @param aliPayId 支付宝id
     * @param price    金额
     * @param request  请求
     * @return 支付宝与微信平台的判断
     * @throws IOException IOException
     */
    @RequestMapping(value = "toWxAliPay.html", produces = "text/html;charset=UTF-8")
    String toWxAliPay(Integer wxPayId, Integer aliPayId, BigDecimal price, HttpServletRequest request) throws IOException;


    /**
     * 支付回调地址 方式一
     * <p>
     * 方式二，{@link #payBack(HttpServletRequest, Integer)} 是属于简化方式， 试用与简单的业务场景
     *
     * @param request 请求
     * @param payId   账户id
     * @return 支付是否成功
     * @throws IOException IOException
     */
    @RequestMapping(value = "payBackOne{payId}.json")
    String payBackOne(HttpServletRequest request, @PathVariable Integer payId) throws IOException;


    /**
     * 支付回调地址
     * 方式二
     *
     * @param request 请求
     * @param payId   账户id
     * @return 支付是否成功
     * @throws IOException IOException
     *                     拦截器相关增加， 详情查看{@link PayService#addPayMessageInterceptor(PayMessageInterceptor)}
     *                     <p>
     *                     业务处理在对应的PayMessageHandler里面处理，在哪里设置PayMessageHandler，详情查看{@link PayService#setPayMessageHandler(PayMessageHandler)}
     *                     </p>
     *                     如果未设置 {@link PayMessageHandler} 那么会使用默认的 {@link DefaultPayMessageHandler}
     */
    @RequestMapping(value = "payBack{payId}.json")
    String payBack(HttpServletRequest request, @PathVariable Integer payId) throws IOException;


    /**
     * 查询
     *
     * @param order 订单的请求体
     * @return 返回查询回来的结果集，支付方原值返回
     */
    @RequestMapping("query")
    Map<String, Object> query(QueryOrder order);

    /**
     * 交易关闭接口
     *
     * @param order 订单的请求体
     * @return 返回支付方交易关闭后的结果
     */
    @RequestMapping("close")
    Map<String, Object> close(QueryOrder order);

    /**
     * 申请退款接口
     *
     * @param payId 账户id
     * @param order 订单的请求体
     * @return 返回支付方申请退款后的结果
     */
    @RequestMapping("refund")
    RefundResult refund(Integer payId, RefundOrder order);

    /**
     * 查询退款
     *
     * @param order 订单的请求体
     * @return 返回支付方查询退款后的结果
     */
    @RequestMapping("refundquery")
    Map<String, Object> refundquery(Integer payId, RefundOrder order);

    /**
     * 下载对账单
     *
     * @param order 订单的请求体
     * @return 返回支付方下载对账单的结果
     */
    @RequestMapping("downloadbill")
    Object downloadbill(QueryOrder order);


    /**
     * 转账
     *
     * @param payId 账户id
     * @param order 转账订单
     * @return 对应的转账结果
     */
    @RequestMapping("transfer")
    Map<String, Object> transfer(int payId, TransferOrder order);

    /**
     * 转账查询
     *
     * @param payId   账户id
     * @param outNo   商户转账订单号
     * @param tradeNo 支付平台转账订单号
     * @return 对应的转账订单
     */
    @RequestMapping("transferQuery")
    Map<String, Object> transferQuery(int payId, String outNo, String tradeNo);

}
