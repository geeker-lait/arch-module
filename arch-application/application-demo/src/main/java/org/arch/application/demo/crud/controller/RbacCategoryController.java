package org.arch.application.demo.crud.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.application.demo.crud.dto.RbacCategoryRequest;
import org.arch.application.demo.crud.dto.RbacCategorySearchDto;
import org.arch.application.demo.crud.entity.RbacCategory;
import org.arch.application.demo.crud.service.RbacCategoryService;
import org.arch.framework.crud.CrudController;
import org.arch.framework.crud.Response;
import org.springframework.web.bind.annotation.*;

/**
 * 资源类目表服务控制器
 *
 * @author lait
 * @description
 * @since 2020-11-13 10:30:39
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/rbacCategory")
public class RbacCategoryController implements CrudController {
    private final RbacCategoryService rbacCategoryService;


    @GetMapping(path = "/{id:.+}")
    public void testGet(@PathVariable("id") Long id) {
        log.info("结果：{}",rbacCategoryService.findById(id));
    }

    @DeleteMapping(path = "/{id:.+}")
    public void testDelete(@PathVariable("id") Long id) {
        log.info("结果：{}",rbacCategoryService.deleteById(id));
    }

    @GetMapping
    public void testList() {
        log.info("结果：{}",rbacCategoryService.findAll());
    }

    @PostMapping
    public Response testSave(RbacCategoryRequest rbacCategoryRequest) {
        // 转换
        RbacCategory rbacGroup = rbacCategoryService.mapperByClass(rbacCategoryRequest, RbacCategory.class);
        // 保存
        return Response.success(rbacCategoryService.save(rbacGroup));
    }


    @GetMapping("query")
    public Response testQuery(RbacCategorySearchDto rbacCategorySearchDto) {
        return Response.success(rbacCategoryService.findAllByMapParams(rbacCategorySearchDto.getSearchParams()));
    }


    @GetMapping("page")
    public Response testPage(RbacCategorySearchDto rbacCategorySearchDto, int pageSize, int pageNo) {
        return Response.success(rbacCategoryService.findPage(rbacCategorySearchDto.getSearchParams(), pageNo, pageSize));
    }
}
