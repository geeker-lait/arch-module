package org.arch.application.demo.crud.controller;

import org.arch.application.demo.crud.entity.RbacGroup;
import org.arch.application.demo.crud.service.RbacGroupService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 组织机构表服务控制器
 *
 * @author lait
 * @since 2020-11-13 10:30:39
 * @description 
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/rbacGroup")
public class RbacGroupController{
    private final RbacGroupService rbacGroupService;
    @GetMapping
    public void testGet(){
        System.out.println(rbacGroupService.findAll());
    }

    @PostMapping
    public void testPost(){
        RbacGroup rbacGroup = new RbacGroup();
        rbacGroup.setGroupCode("group-code");
        System.out.println(rbacGroupService.save(rbacGroup));
    }
}
