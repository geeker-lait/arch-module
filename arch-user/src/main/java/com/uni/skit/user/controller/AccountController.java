package com.uni.skit.user.controller;

import com.uni.account.entity.mybatis.AAccount;
import com.uni.account.service.IAAccountService;
import com.uni.common.exception.BizException;
import com.uni.common.sms.ChuangLanSmsService;
import com.uni.common.utils.SecurityUtils;
import com.uni.skit.user.biz.IAccountBiz;
import com.uni.skit.user.dto.AuthAccountDto;
import com.uni.skit.user.dto.AuthRegDto;
import com.uni.skit.user.vo.ApiBaseResult;
import com.uni.skit.user.vo.JwtUserVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Description: 账户相关
 *
 * @author kenzhao
 * @date 2020/3/28 13:36
 */
@Api(tags = "账户相关")
@RestController
public class AccountController {

    @Autowired
    private IAAccountService iaAccountService;
    @Autowired
    private IAccountBiz iAccountBiz;
//    @Autowired
//    private ChuangLanSmsService chuangLanSmsService;


    @ApiOperation(value = "测试获取账户list")
    @GetMapping("/test/accountlist")
    public List<AAccount> getAccount() {
//        //生成验证码
//        String accountCode = RandomStringUtils.random(4, "0123456789");
//        try {
//            chuangLanSmsService.sendSms(accountCode, "18516108779");
//        } catch (Exception e) {
//            throw new BizException("短信发送失败，请重试");
//        }
        return iaAccountService.list();
    }

    @ApiOperation(value = "测试")
    @GetMapping("/index")
    public ApiBaseResult index() {
        return ApiBaseResult.success("Ok");
    }


    @ApiOperation(value = "获取短信验证码")
    @PostMapping("/initauth/getAccountCode")
    public ApiBaseResult getAccountCode(@Validated @RequestBody AuthRegDto authRegDto, HttpServletRequest request) {
        JwtUserVo jwtUserVo = iAccountBiz.getAccountCode(authRegDto,request);
        return ApiBaseResult.success(jwtUserVo);
    }

    @ApiOperation(value = "验证码登录")
    @PostMapping("/initauth/verifiLogin")
    public ApiBaseResult verifiLogin(@Validated @RequestBody AuthRegDto authRegDto, HttpServletRequest request) throws Exception {
        JwtUserVo jwtUserVo = iAccountBiz.verifiLogin(authRegDto);
        return ApiBaseResult.success(jwtUserVo);
    }

    @ApiOperation(value = "注册登录")
    @PostMapping("/initauth/reg")
    public ApiBaseResult reg(@Validated @RequestBody AuthRegDto authRegDto, HttpServletRequest request) throws Exception {
        JwtUserVo jwtUserVo = iAccountBiz.reg(authRegDto);
        return ApiBaseResult.success(jwtUserVo);
    }

    @ApiOperation(value = "登录")
    @PostMapping("/initauth/login")
    public ApiBaseResult login(@Validated @RequestBody AuthAccountDto authAccountDto, HttpServletRequest request) throws Exception {
        JwtUserVo jwtUserVo = iAccountBiz.login(authAccountDto);
        return ApiBaseResult.success(jwtUserVo);
    }

    @ApiOperation(value = "获取当前账户信息", authorizations = @Authorization(value = "token"))
    @GetMapping("/initauth/getAccountInfo")
    public ApiBaseResult getAccountInfo() {
        return ApiBaseResult.success(SecurityUtils.getCurrentUser());
    }

}