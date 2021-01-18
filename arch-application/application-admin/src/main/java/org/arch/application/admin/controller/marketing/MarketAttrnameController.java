package org.arch.application.admin.controller.marketing;

import code.service.MarketAttrnameService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 营销-自定义动态属性服务控制器
 *
 * @author lait
 * @since 2021-01-18 20:28:43
 * @description PN15855012581
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/marketAttrname")
public class MarketAttrnameController{
    private final MarketAttrnameService marketAttrnameService;

}
