package org.arch.application.portal.controller.form;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.arch.framework.beans.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class OperateTableController {

//    @Autowired
//    private FieldService fieldService;
//
//    @Autowired
//    private FormService formService;
//
//    @Autowired
//    private OperateTableService operateTableService;
//
//    /**
//     * 字段保存时，根据字段创建对应的数据库表
//     *
//     * @param formId
//     * @return
//     */
//    @ApiOperation(value = "创建后台数据库表接口", notes = "修改字段页面点击保存后调用该接口，第一调用直接建表，其他时间调用先删表，再重新建表；表单已有数据时，无法修改表！")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "formId", value = "表单Id", required = true, dataType = "Integer", paramType = "path")
//    })
//    @GetMapping("/createFormTable/{formId}")
//    public Response createFormTable(@PathVariable Integer formId) {
//        //校验formId是否存在，
//        Form form = formService.queryOne(formId);
//        if (form != null) {
//            String formTableName = form.getFormTableName();
//            Integer fieldNum = fieldService.countFields(form.getFormId());
//            //校验formId是否配置好字段，字段个数不能为0
//            if (fieldNum > 0) {
//                //查出字段列表，放到List中备用，用来建表
//                List<String> fieldCodeList = operateTableService.getFieldList(formId);
//                Map<String, Object> map = new HashMap<>();
//                map.put("tableName", formTableName);
//                map.put("fieldCodeList", fieldCodeList);
//                if (operateTableService.existTable(formTableName) == 0) { //检查表是否已经存在
//                    //如果不存在，开始建表
//                    operateTableService.createNewTable(map);
//                    return Response.success(null);
//                } else {
//                    //已经存在的表，如果数据行数为0，则drop掉重新create
//                    Map<String, Object> tableNameMap = new HashMap<>();
//                    tableNameMap.put("tableName", formTableName);
//                    Integer rowNum = operateTableService.countTableRow(tableNameMap);
//                    if (rowNum == 0) {
//                        //先drop表
//                        operateTableService.dropTable(tableNameMap);
//                        //再create
//                        operateTableService.createNewTable(map);
//                        return Response.success(null);
//                    } else {
//                        return Response.failed("操作的表单已在数据库中存在且数据不为空！");
//                    }
//                }
//            } else {
//                return Response.failed("操作的表单没有配置字段！");
//            }
//        } else {
//            return Response.failed("操作的表单不存在！");
//        }
//    }
//
//    /**
//     * 获取对应tableName所有的数据行，返回前端
//     *
//     * @param tableName
//     * @return
//     */
//    @ApiOperation(value = "获取表单内容接口", notes = "根据tableName来获取表单中的所有数据行数，进行前端展示")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "tableName", value = "数据库中的表名", required = true, dataType = "String", paramType = "path"),
//            @ApiImplicitParam(name = "page", value = "页码", required = true, dataType = "Integer", paramType = "query"),
//            @ApiImplicitParam(name = "size", value = "每页条数", required = true, dataType = "Integer", paramType = "query")
//    })
//    @GetMapping("/queryAll/{tableName}")
//    public Response queryAll(
//            @PathVariable String tableName,
//            @RequestParam int page,
//            @RequestParam int size
//    ) {
//        Map<String, Object> map = new HashMap<>();
//        map.put("tableName", tableName);
//        PageInfo<Map<String, Object>> list = operateTableService.queryAll(map, page, size);
//        return Response.success(list);
//    }
//
//    /**
//     * 根据表单ID获取所有字段和选项的详情列表返回前端生成动态表单
//     *
//     * @param formId
//     * @return
//     */
//    @ApiOperation(value = "获取所有字段和选项的详情列表", notes = "根据表单ID获取所有字段和选项的详情列表返回前端生成动态表单")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "formId", value = "表单Id", required = true, dataType = "Integer", paramType = "path")
//    })
//    @GetMapping("/getFieldAndOption/{formId}")
//    public Response getFieldAndOptionDetail(@PathVariable Integer formId) {
//        List<FieldAndOptionDetail> list = operateTableService.getFieldAndOptionDetailList(formId);
//        return Response.success(list);
//    }
//
//    /**
//     * 插入数据到动态表中
//     *
//     * @param tableName
//     * @param data
//     * @return
//     */
//    @ApiOperation(value = "新增数据接口", notes = "根据前端拼接的json串，插入数据到表单表中")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "tableName", value = "表名", required = true, dataType = "String", paramType = "query"),
//            @ApiImplicitParam(name = "data", value = "数据拼接的json串", required = true, dataType = "String", paramType = "query")
//    })
//    @PostMapping("/insertTableContent")
//    public Response insertTableContent(
//            @RequestParam String tableName,
//            @RequestParam String data
//    ) {
//        Map<String, String> map = JSONObject.parseObject(data, new TypeReference<Map<String, String>>() {
//        });
//        System.out.println(map);
//        List<String> keyList = new ArrayList<>();
//        List<String> valueList = new ArrayList<>();
//        for (String key : map.keySet()) {
//            keyList.add(key);
//            valueList.add(map.get(key));
//        }
//        System.out.println(keyList);
//        System.out.println(valueList);
//        Map<String, Object> paramMap = new HashMap<>();
//        paramMap.put("keyList", keyList);
//        paramMap.put("valueList", valueList);
//        paramMap.put("tableName", tableName);
//        int num = operateTableService.insertTable(paramMap);
//        if (num <= 0) {
//            return Response.failed("新增数据失败！");
//        } else {
//            return Response.success(null);
//        }
//    }
//
//    /**
//     * 更新数据到动态表中
//     *
//     * @param tableName
//     * @param data
//     * @return
//     */
//
//    @ApiOperation(value = "表单数据更新接口", notes = "表单的数据修改后，动态保存到数据库中。")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "tableName", value = "表名", required = true, dataType = "String", paramType = "query"),
//            @ApiImplicitParam(name = "data", value = "数据拼接的json串", required = true, dataType = "String", paramType = "query")
//    })
//    @PutMapping("/updateTableContent")
//    public Response updateTableContent(
//            @RequestParam String tableName,
//            @RequestParam String data
//    ) {
//        Map<String, String> map = JSONObject.parseObject(data, new TypeReference<Map<String, String>>() {
//        });
//        List<String> keyList = new ArrayList<>();
//        List<String> valueList = new ArrayList<>();
//        int id = 0;
//        for (String key : map.keySet()) {
//            if ("id".equals(key)) {
//                id = Integer.parseInt(map.get("id"));
//            } else {
//                valueList.add(map.get(key));
//                keyList.add(key);
//            }
//        }
//        Map<String, Object> paramMap = new HashMap<>();
//        paramMap.put("valueList", valueList);
//        paramMap.put("tableName", tableName);
//        paramMap.put("keyList", keyList);
//        paramMap.put("id", id);
//        int num = operateTableService.updateTable(paramMap);
//        if (num <= 0) {
//            return Response.failed("更新数据失败！");
//        } else {
//            return Response.success(null);
//        }
//    }
//
//    @ApiOperation(value = "删除表单中的一行数据", notes = "从指定表名的表中根据id删除表单中的一行数据")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "id", value = "数据id", required = true, dataType = "Integer", paramType = "path"),
//            @ApiImplicitParam(name = "tableName", value = "表名", required = true, dataType = "String", paramType = "query"),
//    })
//    @DeleteMapping("/deleteTableContent/{id}")
//    public Response deleteTableContent(
//            @PathVariable Integer id,
//            @RequestParam String tableName
//    ) {
//        Map<String, Object> map = new HashMap<>();
//        map.put("tableName", tableName);
//        map.put("id", id);
//        int num = operateTableService.deleteTable(map);
//        if (num > 0) {
//            return Response.success(null);
//        } else {
//            return Response.failed("删除数据失败！");
//        }
//    }
//
//    @ApiOperation(value = "获取单行数据", notes = "从指定表名中获取指定id的数据")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "id", value = "数据id", required = true, dataType = "Integer", paramType = "path"),
//            @ApiImplicitParam(name = "tableName", value = "表名", required = true, dataType = "String", paramType = "query"),
//    })
//    @GetMapping("/operateTable/{id}")
//    public Response getSingleContent(
//            @PathVariable Integer id,
//            @RequestParam String tableName
//    ) {
//        Map<String, Object> map = new HashMap<>();
//        map.put("id", id);
//        map.put("tableName", tableName);
//        List<Map<String, Object>> list = operateTableService.queryOne(map);
//        System.out.println("id=" + id);
//        System.out.println("tableName=" + tableName);
//        return Response.success(list);
//    }

}
