package org.arch.application.admin.controller.statictics;

import code.service.StaticticsPromotionRateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 统计-推广费率服务控制器
 *
 * @author lait
 * @since 2021-01-18 20:28:43
 * @description PN15855012581
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/staticticsPromotionRate")
public class StaticticsPromotionRateController{
    private final StaticticsPromotionRateService staticticsPromotionRateService;

}
