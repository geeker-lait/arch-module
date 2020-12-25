package com.uni.skit.user.controller;

import com.uni.skit.user.biz.IPromotionBiz;
import com.uni.skit.user.dto.PromotionDto;
import com.uni.skit.user.vo.ApiBaseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Description: 推广
 *
 * @author kenzhao
 * @date 2020/4/17 14:55
 */
@Api(tags = "推广")
@RestController
public class PromotionController {
    @Autowired
    private IPromotionBiz promotionBiz;

    @ApiOperation(value = "获取短信验证码", notes = "发送短信 提供最新版本的地址")
    @PostMapping("/promotion/getSmsCode")
    public ApiBaseResult sendSms(@Validated PromotionDto promotionDto, HttpServletRequest request) {
        return promotionBiz.sendSms(promotionDto,request);
    }

    @ApiOperation(value = "获取最新版本的地址", notes = "提供最新版本的地址")
    @PostMapping("/promotion/getAppVersion")
    public ApiBaseResult getAppVersion(@Validated @RequestBody PromotionDto promotionDto, HttpServletRequest request) {
        return promotionBiz.getAppVersion(promotionDto,request);
    }

    @ApiOperation(value = "获取app内的常用信息", notes = "获取app内的常用信息")
    @PostMapping("/promotion/appInfo")
    public ApiBaseResult appInfo(@Validated @RequestBody PromotionDto promotionDto, HttpServletRequest request) {
        return promotionBiz.getAppInfo(promotionDto,request);
    }
}