package org.arch.auth.sso.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.arch.auth.sso.request.bind.RegisterRequest;
import org.arch.framework.crud.Response;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 注册相关接口
 * @author YongWu zheng
 * @since 2021.1.3 16:25
 */
@Api(tags = "注册相关")
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class RegisterController {

//    private final BizAccountRegisterService bizAccountRegisterService;

    /**
     * 用户名密码注册请求
     * @param registerRequest   注册请求
     * @return  返回是否注册成功信息
     */
    @RequestMapping(value = "/reg", method = {RequestMethod.POST})
    @ApiOperation(value = "通过用户名密码等信息注册账号", httpMethod = "POST")
    public Response<String> register(RegisterRequest registerRequest) {

        // TODO 是否自动登录操作
//        AuthTokenVo authTokenVo = bizAccountRegisterService.register(registerRequest);

//        return ResponseResult.success("注册成功", authTokenVo);
        return Response.success(null, "注册成功");
    }
}
