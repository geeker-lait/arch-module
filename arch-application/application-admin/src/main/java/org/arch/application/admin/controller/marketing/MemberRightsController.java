package org.arch.application.admin.controller.marketing;

import code.service.MemberRightsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 会员权益服务控制器
 *
 * @author lait
 * @since 2021-01-18 20:28:43
 * @description PN15855012581
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/memberRights")
public class MemberRightsController{
    private final MemberRightsService memberRightsService;

}
