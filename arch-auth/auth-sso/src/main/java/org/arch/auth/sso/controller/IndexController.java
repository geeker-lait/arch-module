package org.arch.auth.sso.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.ums.bean.TokenInfo;
import org.arch.framework.utils.SecurityUtils;
import org.arch.framework.utils.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 首页控制器
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.1.5 18:52
 */
@Controller
@RequiredArgsConstructor
@Slf4j
public class IndexController {

    @RequestMapping(value = "/", method = {RequestMethod.GET, RequestMethod.POST})
    public String index(Model model) {
        // todo
        try {
            TokenInfo currentUser = SecurityUtils.getCurrentUser();
            model.addAttribute("username", currentUser.getAccountName());
            model.addAttribute("roles", StringUtils.join(currentUser.getAuthorities(),","));
        }
        catch (Exception e) {
            return "index";
        }
        return "index";
    }

}
