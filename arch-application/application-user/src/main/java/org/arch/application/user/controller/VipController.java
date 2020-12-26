package org.arch.application.user.controller;

import com.uni.common.entity.TokenInfo;
import com.uni.common.exception.BadRequestException;
import com.uni.common.utils.SecurityUtils;
import org.arch.ums.account.dto.AuthAccountDto;
import org.arch.ums.account.vo.ApiBaseResult;
import com.uni.vip88.service.IVipService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description: 会员权益信息
 *
 * @author kenzhao
 * @date 2020/4/15 15:24
 */
@Api(tags = "账户相关")
@RestController
public class VipController {
    @Autowired
    private IVipService vipService;

    @ApiOperation(value = "查询会员权益信息", authorizations = @Authorization(value = "token"))
    @PostMapping("/vip/geturl")
    public ApiBaseResult geturl() {
        return ApiBaseResult.success(vipService.vipUrl());
    }

    @ApiOperation(value = "重新开通会员权益", authorizations = @Authorization(value = "token"))
    @PostMapping("/vip/get")
    public ApiBaseResult getVip(@RequestBody AuthAccountDto authAccountDto) {
        TokenInfo tokenInfo = SecurityUtils.getCurrentUser();
        if (!"18516108772".equals(tokenInfo.getAccountName())) {
            throw new BadRequestException("无重新开通会员权益权限");
        }
        return ApiBaseResult.success(vipService.vipPay(authAccountDto.getAppId(),authAccountDto.getAccountName()));
    }
}