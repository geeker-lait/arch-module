package org.arch.application.demo.crud.controller;

import org.arch.application.demo.crud.entity.RbacCategory;
import org.arch.application.demo.crud.service.RbacCategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 资源类目表服务控制器
 *
 * @author lait
 * @since 2020-11-13 10:30:39
 * @description 
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/rbacCategory")
public class RbacCategoryController{
    private final RbacCategoryService rbacCategoryService;


    @GetMapping
    public void test(){
        System.out.println(rbacCategoryService.findAll());

    }

    @PostMapping
    public void testPost(){
        //rbacGroupService.conver()
        RbacCategory rbacGroup = new RbacCategory();
        rbacGroup.setCategoryName("category-code");
        System.out.println(rbacCategoryService.save(rbacGroup));
    }

}
