package org.arch.application.admin.controller.ums.user;

import code.service.UserRelativesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 用户-亲朋信息服务控制器
 *
 * @author lait
 * @since 2021-01-18 20:28:43
 * @description PN15855012581
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/userRelatives")
public class UserRelativesController{
    private final UserRelativesService userRelativesService;

}
