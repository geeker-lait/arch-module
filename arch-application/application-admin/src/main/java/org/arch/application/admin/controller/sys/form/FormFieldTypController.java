package org.arch.application.admin.controller.sys.form;

import code.service.FormFieldTypService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 表单字段类型服务控制器
 *
 * @author lait
 * @since 2021-01-18 20:28:43
 * @description PN15855012581
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/formFieldTyp")
public class FormFieldTypController{
    private final FormFieldTypService formFieldTypService;

}
