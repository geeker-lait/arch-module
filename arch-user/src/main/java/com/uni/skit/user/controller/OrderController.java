package com.uni.skit.user.controller;

import com.uni.skit.user.biz.IOrderBiz;
import com.uni.skit.user.dto.OrderDto;
import com.uni.skit.user.vo.ApiBaseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description: 订单信息
 *
 * @author kenzhao
 * @date 2020/3/30 19:04
 */
@Api(tags = "订单信息")
@RestController
public class OrderController {
    @Autowired
    private IOrderBiz orderBiz;

    @ApiOperation(value = "查询用户订单信息", authorizations = @Authorization(value = "token"))
    @PostMapping("/product/getOrders")
    public ApiBaseResult getOrders(@Validated @RequestBody OrderDto orderDto) {
        return orderBiz.getOrders(orderDto);
    }


}