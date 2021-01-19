package org.arch.application.admin.controller.ums.user;

import code.service.UserIdcardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 用户-身份信息服务控制器
 *
 * @author lait
 * @since 2021-01-18 20:28:43
 * @description PN15855012581
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/userIdcard")
public class UserIdcardController{
    private final UserIdcardService userIdcardService;

}
