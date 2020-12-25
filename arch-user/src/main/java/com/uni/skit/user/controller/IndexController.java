package com.uni.skit.user.controller;

import com.uni.skit.user.biz.IIndexBiz;
import com.uni.skit.user.vo.ApiBaseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Description: 首页信息
 *
 * @author kenzhao
 * @date 2020/4/14 17:37
 */
@Api(tags = "首页信息")
@RestController
public class IndexController {

    @Autowired
    private IIndexBiz indexBiz;

    @ApiOperation(value = "首页信息", notes = "立即拿钱 LJNQ;  基本信息 JBXX;  工作信息 GZXX;  联系人信息 LXXX;  身份认证 SFRZ; 银行绑卡 YHBK 贷款app  DKAPP", authorizations = @Authorization(value = "token"))
    @PostMapping("/index/getInfo")
    public ApiBaseResult getBankCards(HttpServletRequest request) {
        return indexBiz.index();
    }
}