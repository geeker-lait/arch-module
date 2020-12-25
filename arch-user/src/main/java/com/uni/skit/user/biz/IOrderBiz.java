package com.uni.skit.user.biz;

import com.uni.pay.core.PayResponse;
import com.uni.skit.user.dto.OrderDto;
import com.uni.skit.user.vo.ApiBaseResult;

/**
 * Description: 订单相关业务
 *
 * @author kenzhao
 * @date 2020/4/14 11:27
 */
public interface IOrderBiz {
    /**
     * 新增订单
     * @return
     */
    ApiBaseResult addOrder(OrderDto orderDto);
    /**
     * 修改订单
     * @return
     */
    ApiBaseResult updateOrder(OrderDto orderDto,PayResponse payResponse);
    /**
     * 查询订单列表
     * @return
     */
    ApiBaseResult getOrders(OrderDto orderDto);
    /**
     * 用户是否存在支付成功订单
     * @return
     */
    ApiBaseResult getPayOrders();
}