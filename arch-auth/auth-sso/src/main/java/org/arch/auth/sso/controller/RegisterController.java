package org.arch.auth.sso.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.arch.auth.sso.request.bind.UsernameRegisterRequest;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import top.dcenter.ums.security.common.vo.ResponseResult;

import static org.arch.auth.sso.utils.RegisterUtils.ACCOUNT_TYPE_HEADER_NAME;

/**
 * 注册相关接口
 * @author YongWu zheng
 * @since 2021.1.3 16:25
 */
@Api(tags = "注册相关")
@RestController
@RequestMapping("/user")
public class RegisterController {


    /**
     * 用户名密码注册请求
     * @param accountType               通过请求头传递的账号类型
     * @param usernameRegisterRequest   用户名密码注册请求
     * @return  返回是否注册成功信息
     */
    @RequestMapping(value = "/reg", method = {RequestMethod.POST}, headers = {ACCOUNT_TYPE_HEADER_NAME})
    @ApiOperation(value = "通过用户名密码等信息注册账号", httpMethod = "POST")
    public ResponseResult register(@RequestHeader(ACCOUNT_TYPE_HEADER_NAME) String accountType,
                                   UsernameRegisterRequest usernameRegisterRequest) {
        // TODO ...

        // TODO 是否自动登录操作
        return ResponseResult.success("注册成功");
    }
}
