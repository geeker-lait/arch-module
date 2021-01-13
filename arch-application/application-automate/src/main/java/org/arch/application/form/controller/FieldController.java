package org.arch.application.form.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.arch.application.form.domain.Field;
import org.arch.application.form.domain.FieldType;
import org.arch.application.form.domain.Option;
import org.arch.application.form.service.FieldService;
import org.arch.framework.crud.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FieldController {

    @Autowired
    private FieldService fieldService;

    @ApiOperation(value = "新增字段", notes = "表单新增字段")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "formId", value = "归属表单Id", required = true, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "fieldType", value = "字段类型", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "fieldName", value = "字段名称", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "fieldDesc", value = "字段描述", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "fieldOrder", value = "字段排序", dataType = "String", paramType = "query")
    })
    @PostMapping("/field")
    public Response addForm(
            @RequestParam(value = "formId", defaultValue = "") Integer formId,
            @RequestParam(value = "fieldType", defaultValue = "") String fieldType,
            @RequestParam(value = "fieldName", defaultValue = "") String fieldName,
            @RequestParam(value = "fieldDesc", defaultValue = "") String fieldDesc,
            @RequestParam(value = "fieldOrder", defaultValue = "") String fieldOrder) {
        if ("".equals(fieldType) || fieldType == null
                || "".equals(fieldName) || fieldName == null
                || formId == null
                || "".equals(fieldOrder) || fieldOrder == null) {
            return Response.failed("请按照要求填写字段！");
        } else {
            Field field = new Field();
            field.setFormId(formId);
            field.setFieldType(fieldType);
            field.setFieldName(fieldName);
            field.setFieldDesc(fieldDesc);
            field.setFieldOrder(fieldOrder);
            System.out.println(field.toString());
            int num = fieldService.insert(field);
            if (num > 0) {
                return Response.success(null);
            } else if (num == -1) {
                return Response.failed("表单已有数据，字段无法新增和删除！");
            } else if (num == -2) {
                return Response.failed("字段名称重复！");
            } else {
                return Response.failed("新增字段失败!");
            }
        }
    }


    @ApiOperation(value = "查询字段列表", notes = "根据表单ID获取字段列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "formId", value = "表单ID", required = true, dataType = "Integer", paramType = "query")
    })
    @GetMapping("/field")
    public Response queryAllForm(
            @RequestParam(value = "formId") Integer formId
    ) {
        List<Field> fieldList = fieldService.queryAll(formId);
        return Response.success(fieldList);
    }

    @ApiOperation(value = "查询字段列表", notes = "根据表单ID先查询数据库表是否建立，建立后返回字段列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "formId", value = "表单ID", required = true, dataType = "Integer", paramType = "query")
    })
    @GetMapping("/fieldList")
    public Response ifTableExistAndFieldList(
            @RequestParam(value = "formId") Integer formId
    ) {
        List<Field> fieldList = fieldService.ifTableExistAndFieldList(formId);
        return Response.success(fieldList);
    }

    @ApiOperation(value = "查询单个字段详情", notes = "查询单个字段详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "fieldId", value = "字段Id", required = true, dataType = "Integer", paramType = "path")
    })
    @GetMapping("/field/{fieldId}")
    public Response queryOneForm(@PathVariable Integer fieldId) {
        if (fieldId == null) {
            return Response.failed("请输入正确的字段ID");
        } else {
            Field field = fieldService.queryOne(fieldId);
            if (field == null) {
                return Response.failed("查询的表单不存在");
            } else {
                return Response.success(field);
            }
        }
    }

    @ApiOperation(value = "删除字段", notes = "删除单个字段")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "fieldId", value = "字段Id", required = true, dataType = "Integer", paramType = "path")
    })
    @DeleteMapping("/field/{fieldId}")
    public Response delForm(@PathVariable Integer fieldId) {
        if (fieldId == null) {
            return Response.failed("请输入正确的字段ID");
        } else {
            //checkIfFieldCouldDelete()
            int num = fieldService.deleteById(fieldId);
            if (num > 0) {
                return Response.success();
            } else if (num == -1) {
                return Response.failed("该字段已有数据，无法删除！");
            } else {
                return Response.failed("删除字段失败！");
            }
        }
    }

    @ApiOperation(value = "更新字段", notes = "更新字段")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "formId", value = "表单Id", required = true, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "fieldId", value = "字段Id", required = true, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "fieldType", value = "字段类型", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "fieldName", value = "字段名称", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "fieldDesc", value = "字段描述", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "fieldOrder", value = "字段排序", dataType = "String", paramType = "query")
    })
    @PutMapping("/field")
    public Response updateForm(
            @RequestParam(value = "fieldId") Integer fieldId,
            @RequestParam(value = "formId") Integer formId,
            @RequestParam(value = "fieldType", defaultValue = "") String fieldType,
            @RequestParam(value = "fieldName", defaultValue = "") String fieldName,
            @RequestParam(value = "fieldDesc", defaultValue = "") String fieldDesc,
            @RequestParam(value = "fieldOrder", defaultValue = "") String fieldOrder) {
        int num = 0;
        if ("".equals(fieldType) || fieldType == null
                || "".equals(fieldName) || fieldName == null
                || "".equals(fieldOrder) || fieldOrder == null) {
            return Response.failed("表单名称不能为空！");
        } else {
            Field field = new Field();
            field.setFormId(formId);
            field.setFieldId(fieldId);
            field.setFieldType(fieldType);
            field.setFieldName(fieldName);
            field.setFieldDesc(fieldDesc);
            field.setFieldOrder(fieldOrder);
            num = fieldService.update(field);
        }
        if (num > 0) {
            return Response.success();
        } else if (num == -2) {
            return Response.failed("字段名称重复！");
        } else {
            return Response.failed("更新失败！");
        }

    }


    @ApiOperation(value = "查询字段类型列表", notes = "查询字段类型列表")
    @GetMapping("/fieldType")
    public Response queryfieldTypeList() {
        List<FieldType> fieldList = fieldService.getFieldTypes();
        return Response.success(fieldList);
    }


    @ApiOperation(value = "查询字段选项列表", notes = "查询字段选项")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "fieldId", value = "字段Id", required = true, dataType = "Integer", paramType = "path")
    })
    @GetMapping("/option/{fieldId}")
    public Response queryOptionList(
            @PathVariable Integer fieldId
    ) {
        List<Option> optionList = fieldService.getOptionList(fieldId);
        return Response.success(optionList);
    }


    @ApiOperation(value = "新增选项", notes = "字段新增选项")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "fieldId", value = "归属字段Id", required = true, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "optionName", value = "字段类型", required = true, dataType = "String", paramType = "query")
    })
    @PostMapping("/option")
    public Response addOption(
            @RequestParam(value = "fieldId", defaultValue = "") Integer fieldId,
            @RequestParam(value = "optionName", defaultValue = "") String optionName
    ) {
        if ("".equals(optionName) || optionName == null) {
            return Response.failed("选项名称不能为空");
        } else {
            Option option = new Option();
            option.setFieldId(fieldId);
            option.setOptionName(optionName);
            int num = fieldService.insertOption(option);
            if (num > 0) {
                return Response.success();
            } else if (num == -1) {
                return Response.failed("选项名称重复！");
            } else {
                return Response.failed("新增选项失败!");
            }
        }
    }

    @ApiOperation(value = "删除字段", notes = "删除单个字段")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "optionId", value = "字段Id", required = true, dataType = "Integer", paramType = "path")
    })
    @DeleteMapping("/option/{optionId}")
    public Response delOption(@PathVariable Integer optionId) {
        if (optionId == null) {
            return Response.failed("请输入正确的选项ID");
        } else {
            int num = fieldService.deleteOption(optionId);
            if (num > 0) {
                return Response.success();
            } else {
                return Response.failed("删除选项失败！");
            }
        }
    }
}
