package org.arch.application.admin.controller.sys.form;

import code.service.FormFieldOptionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 表单字段选项服务控制器
 *
 * @author lait
 * @since 2021-01-18 20:28:43
 * @description PN15855012581
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/formFieldOption")
public class FormFieldOptionController{
    private final FormFieldOptionService formFieldOptionService;

}
