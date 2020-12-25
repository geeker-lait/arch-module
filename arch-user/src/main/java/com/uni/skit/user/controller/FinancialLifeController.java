package com.uni.skit.user.controller;

import com.uni.skit.user.biz.IFinanceLifeBiz;
import com.uni.skit.user.vo.ApiBaseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description: 金融生活
 *
 * @author kenzhao
 * @date 2020/4/15 17:29
 */
@Api(tags = "金融生活")
@RestController
public class FinancialLifeController {
    @Autowired
    private IFinanceLifeBiz financeLifeBiz;

    @ApiOperation(value = "查询金融生活", authorizations = @Authorization(value = "token"))
    @PostMapping("/life/getLifes")
    public ApiBaseResult getLifes() {
        return ApiBaseResult.success(financeLifeBiz.getLifes());
    }

    @ApiOperation(value = "查询会员类型",notes = "vipUrl 有值的是付费会员，否则为未付费会员" ,authorizations = @Authorization(value = "token"))
    @PostMapping("/life/getLifeType")
    public ApiBaseResult getLifeType() {
        return ApiBaseResult.success(financeLifeBiz.getLifeType());
    }

}