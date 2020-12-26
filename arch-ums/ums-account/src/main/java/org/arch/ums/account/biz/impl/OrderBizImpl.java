package org.arch.ums.account.biz.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.uni.common.constant.BizIdCode;
import com.uni.common.constant.RedisKeyCode;
import com.uni.common.entity.TokenInfo;
import com.uni.common.exception.BadRequestException;
import com.uni.common.service.IdService;
import com.uni.common.utils.RedisUtils;
import com.uni.common.utils.SecurityUtils;
import com.uni.order.entity.mybatis.OrderItem;
import com.uni.order.entity.mybatis.OrderMaster;
import com.uni.order.entity.mybatis.OrderPayment;
import com.uni.order.service.IOrderItemService;
import com.uni.order.service.IOrderMasterService;
import com.uni.order.service.IOrderPaymentService;
import com.uni.pay.core.PayResponse;
import org.arch.ums.account.biz.IOrderBiz;
import org.arch.ums.account.dto.OrderDto;
import org.arch.ums.account.vo.ApiBaseResult;
import com.uni.vip88.demain.VipRequestResponse;
import com.uni.vip88.service.IVipService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Description: 订单相关业务
 *
 * @author kenzhao
 * @date 2020/4/14 11:30
 */
@Service
@Slf4j
public class OrderBizImpl implements IOrderBiz {
    @Autowired
    private IOrderMasterService orderMasterService;
    @Autowired
    private IOrderItemService orderItemService;
    @Autowired
    private IOrderPaymentService orderPaymentService;
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private IdService idService;
    @Autowired
    private IVipService vipService;
    @Autowired
    private Environment env;
    /**
     * 新增订单
     *
     * @return
     */
    @Override
    public ApiBaseResult addOrder(OrderDto orderDto) {
        String orderId = idService.generateId(BizIdCode.ORDER);
        OrderMaster orderMaster = new OrderMaster();
        TokenInfo tokenInfo = SecurityUtils.getCurrentUser();
        orderMaster.setAppId(tokenInfo.getAppId());
        orderMaster.setAccountId(tokenInfo.getAccountId());
        OrderMaster getOrderMaster = orderMasterService.getOne(new QueryWrapper<>(orderMaster));


        OrderPayment orderPayment = new OrderPayment();
        if (getOrderMaster == null) {
            //保存主表
            orderMaster.setOrderId(orderId);
            orderMaster.setAccountName(tokenInfo.getAccountName());
            orderMaster.setAmount(BigDecimal.ONE);
            orderMasterService.save(orderMaster);
            //保存子表
            OrderItem orderItem = new OrderItem();
            orderItem.setOrderId(orderId);
            orderItem.setAppId(tokenInfo.getAppId());
            orderItem.setProductId(orderDto.getProductId());
            orderItemService.save(orderItem);
            orderPayment.setOrderId(orderId);
        }else {
            orderPayment.setOrderId(getOrderMaster.getOrderId());
        }
        //保存订单支付表
        orderPayment.setPaymentId(orderDto.getPaymentId());
        orderPayment.setAppId(tokenInfo.getAppId());
        orderPayment.setAccountId(tokenInfo.getAccountId());
        orderPayment.setAmount(BigDecimal.ONE);
        orderPaymentService.save(orderPayment);
        return ApiBaseResult.success();
    }

    /**
     * 修改订单
     *
     * @return
     */
    @Override
    public ApiBaseResult updateOrder(OrderDto orderDto, PayResponse payResponse) {
        TokenInfo tokenInfo = SecurityUtils.getCurrentUser();
        OrderPayment orderPayment = new OrderPayment();
        orderPayment.setPaymentId(orderDto.getPaymentId());
        orderPayment.setAppId(tokenInfo.getAppId());
        orderPayment.setAccountId(tokenInfo.getAccountId());
        orderPayment = orderPaymentService.getOne(new QueryWrapper<>(orderPayment));

        if (orderPayment == null) {
            throw new BadRequestException("支付订单未找到,订单编号:" + orderPayment.getOrderId());
        }

        //保存主表
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setOrderId(orderPayment.getOrderId());
        orderMaster.setAppId(tokenInfo.getAppId());
        orderMaster.setAccountId(tokenInfo.getAccountId());
        orderMaster = orderMasterService.getOne(new QueryWrapper<>(orderMaster));
        if (orderMaster != null ) {
            orderMaster.setOrderStatus(orderDto.getOrderStatus());
            orderMaster.setPayStatus(orderDto.getOrderStatus());
            orderMaster.setPayAmount(payResponse.getAmount());
            orderMasterService.updateById(orderMaster);
            //保存订单支付表
            if (!"success".equals(orderPayment.getPayStatus())) {
                orderPayment.setFinishTime(LocalDateTime.now());
                orderPayment.setAmount(payResponse.getAmount());
                orderPayment.setPayStatus(orderDto.getOrderStatus());
                orderPaymentService.updateById(orderPayment);
            }

            //开通权益
            if ("success".equals(orderDto.getOrderStatus())) {
                VipRequestResponse vipRequestResponse = vipService.vipPay();
                //重新开通权益
                vipPay(vipRequestResponse,orderPayment.getOrderId());
            }
            return ApiBaseResult.success();
        }
        throw new BadRequestException("订单状态更新失败,订单编号:" + orderPayment.getOrderId());
    }

    private void vipPay(VipRequestResponse vipRequestResponse,String orderId){
        if (vipRequestResponse == null) {
            redisUtils.incr(RedisKeyCode.ORDER_VIP_COUNT + orderId, 1);
            String count = redisUtils.getStr(RedisKeyCode.ORDER_VIP_COUNT + orderId);
            Integer countMax = Integer.valueOf(env.getProperty("vip88.pay.maxcount"));
            if (Integer.valueOf(count) <= countMax) {
                //重新开通权益
                vipRequestResponse = vipService.vipPay();
                vipPay(vipRequestResponse,orderId);
            }
        }
    }

    /**
     * 查询订单列表
     *
     * @param orderDto
     * @return
     */
    @Override
    public ApiBaseResult getOrders(OrderDto orderDto) {
        OrderMaster orderMaster = new OrderMaster();
        TokenInfo tokenInfo = SecurityUtils.getCurrentUser();
        orderMaster.setAppId(tokenInfo.getAppId());
        orderMaster.setAccountId(tokenInfo.getAccountId());
        List<OrderMaster> orderMasters  = orderMasterService.list(new QueryWrapper<>(orderMaster));
        return ApiBaseResult.success(orderMasters);
    }

    /**
     * 用户是否存在支付成功订单
     *
     * @return
     */
    @Override
    public ApiBaseResult getPayOrders() {
        TokenInfo tokenInfo = SecurityUtils.getCurrentUser();
        QueryWrapper<OrderMaster> orderMasterQueryWrapper = new QueryWrapper<>();
        orderMasterQueryWrapper.lambda().eq(OrderMaster::getAppId, tokenInfo.getAppId()).eq(OrderMaster::getAccountName, tokenInfo.getAccountName()).eq(OrderMaster::getOrderStatus,"success");
        List orderMasters = orderMasterService.list(orderMasterQueryWrapper);
        if (orderMasters == null || orderMasters.size() < 1) {
            //失败
            return  ApiBaseResult.error("未支付");
        }
        return ApiBaseResult.success(orderMasters);
    }
}