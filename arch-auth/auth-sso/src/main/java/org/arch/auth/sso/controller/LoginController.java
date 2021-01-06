package org.arch.auth.sso.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import top.dcenter.ums.security.common.vo.ResponseResult;

import java.security.Principal;
import java.util.Map;

/**
 * 登录控制器
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.1.5 11:54
 */
@Controller
@Api(value = "登录相关")
public class LoginController {

    @ApiOperation(value = "跳转登录页", httpMethod = "GET")
    @RequestMapping(value = "/login", method = {RequestMethod.GET})
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/me", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseResult me(Principal principal, Model model) {
        // TODO 测试用例
        model.addAttribute("userDetails", principal);
        model.addAttribute("authentication", SecurityContextHolder.getContext().getAuthentication());
        Map<String, Object> data = model.asMap();
        return ResponseResult.success(null, data);
    }

}
