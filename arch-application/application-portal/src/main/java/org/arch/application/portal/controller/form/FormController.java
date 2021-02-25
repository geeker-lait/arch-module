//package org.arch.application.portal.controller.form;
//
//import com.github.pagehelper.PageInfo;
//import io.swagger.annotations.ApiImplicitParam;
//import io.swagger.annotations.ApiImplicitParams;
//import io.swagger.annotations.ApiOperation;
//import org.arch.framework.automate.from.entity.bak.Form;
//import org.arch.framework.automate.from.entity.bak.FormAndRows;
//import org.arch.framework.automate.from.service.bak.FormService;
//import org.arch.framework.beans.Response;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//public class FormController {
//
//    @Autowired
//    private FormService formService;
//
//    @ApiOperation(value = "新增表单", notes = "填写表单名称和表单描述新增表单")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "formName", value = "表单名称", required = true, dataType = "String", paramType = "query"),
//            @ApiImplicitParam(name = "formDesc", value = "表单描述", dataType = "String", paramType = "query")
//    })
//    @PostMapping("/form")
//    public Response addForm(
//            @RequestParam(value = "formName", defaultValue = "") String formName,
//            @RequestParam(value = "formDesc", defaultValue = "") String formDesc) {
//        if ("".equals(formName) || formName == null) {
//            return Response.failed("表单名称不能为空!");
//        } else {
//            Form form = new Form();
//            form.setFormName(formName);
//            form.setFormDesc(formDesc);
//            int num = formService.insert(form);
//            if (num > 0) {
//                return Response.success(null);
//            } else if (num == -1) {
//                return Response.failed("表单名称已存在");
//            } else {
//                return Response.failed("新增表单失败!");
//            }
//        }
//    }
//
//    @ApiOperation(value = "查询表单列表", notes = "查询表单列表")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "searchValue", value = "模糊搜索内容", required = false, dataType = "String", paramType = "query"),
//            @ApiImplicitParam(name = "page", value = "页码", required = true, dataType = "Integer", paramType = "query"),
//            @ApiImplicitParam(name = "size", value = "每页条数", required = true, dataType = "Integer", paramType = "query")
//    })
//    @GetMapping("/form")
//    public Response queryAllForm(
//            @RequestParam(value = "searchValue", defaultValue = "") String searchValue,
//            @RequestParam(value = "page", defaultValue = "1") Integer page,
//            @RequestParam(value = "size", defaultValue = "10") Integer size) {
//        PageInfo<FormAndRows> pageInfo = formService.queryFormAndRows(searchValue, page, size);
//
//        if (pageInfo != null) {
//            return Response.success(null);
//        } else {
//            return Response.failed("查询表单列表失败!");
//        }
//    }
//
//    @ApiOperation(value = "查询单个表单详情", notes = "查询单个表单详情")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "formId", value = "表单Id", required = true, dataType = "Integer", paramType = "path")
//    })
//    @GetMapping("/form/{formId}")
//    public Response queryOneForm(@PathVariable Integer formId) {
//        if (formId == null) {
//            return Response.failed("请输入正确的表单ID");
//        } else {
//            Form form = formService.queryOne(formId);
//            if (form == null) {
//                return Response.failed("查询的表单不存在");
//            } else {
//                return Response.success();
//            }
//        }
//    }
//
//    @ApiOperation(value = "删除表单", notes = "删除表单")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "formId", value = "表单Id", required = true, dataType = "Integer", paramType = "path")
//    })
//    @DeleteMapping("/form/{formId}")
//    public Response delForm(@PathVariable Integer formId) {
//        if (formId == null) {
//            return Response.failed("请输入正确的表单ID");
//        } else {
//            System.out.println("formId = " + formId);
//            int num = formService.deleteById(formId);
//            if (num > 0) {
//                return Response.success();
//            } else if (num == -1) {
//                return Response.failed("表单中有数据无法删除！");
//            } else {
//                return Response.failed("删除失败！");
//            }
//        }
//    }
//
//    @ApiOperation(value = "更新表单", notes = "更新表单")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "formId", value = "表单Id", required = true, dataType = "Integer", paramType = "query"),
//            @ApiImplicitParam(name = "formName", value = "表单名称", required = true, dataType = "String", paramType = "query"),
//            @ApiImplicitParam(name = "formDesc", value = "表单描述", dataType = "String", paramType = "query")
//    })
//    @PutMapping("/form")
//    public Response updateForm(
//            @RequestParam(value = "formId") Integer formId,
//            @RequestParam(value = "formName", defaultValue = "") String formName,
//            @RequestParam(value = "formDesc", defaultValue = "") String formDesc) {
//        if (formName == "" || formName == null) {
//            return Response.failed("表单名称不能为空！");
//        } else {
//            Form form = new Form();
//            form.setFormId(formId);
//            form.setFormName(formName);
//            form.setFormDesc(formDesc);
//            formService.update(form);
//        }
//        return Response.success();
//    }
//
//
//}
