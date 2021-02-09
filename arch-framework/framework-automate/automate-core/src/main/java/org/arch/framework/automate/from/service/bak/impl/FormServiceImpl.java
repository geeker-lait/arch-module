package org.arch.framework.automate.from.service.bak.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import org.arch.framework.automate.from.entity.bak.Form;
import org.arch.framework.automate.from.entity.bak.FormAndRows;
import org.arch.framework.automate.from.mapper.bak.FieldMapper;
import org.arch.framework.automate.from.mapper.bak.FormMapper;
import org.arch.framework.automate.from.mapper.bak.OperateTableMapper;
import org.arch.framework.automate.from.service.bak.FormService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class FormServiceImpl implements FormService {

    private final FormMapper formMapper;
    private final FieldMapper fieldMapper;
    private final OperateTableMapper operateTableMapper;

    @Override
    //事务，保证table_name不为空
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public int insert(Form obj) {
        int result = 0;
        //此处先验证formName是否重复
        if (formMapper.countByFormName(obj.getFormName()) > 0) {
            System.out.println("查询成功");
            return -1;
        } else {
            System.out.println("add开始");
            result = formMapper.insert(obj);
            System.out.println("add成功");
            String formTableName = "upload_form_table" + obj.getFormId();
            System.out.println(obj.getFormTableName());
            obj.setFormTableName(formTableName);
            System.out.println(obj.getFormTableName());
            System.out.println(obj.getFormId());
            formMapper.updateFormTableName(obj);
            HashMap<String, Object> stringObjectHashMap = new HashMap<>();
            stringObjectHashMap.put("tableName", obj.getFormTableName());
            stringObjectHashMap.put("comment", obj.getFormDesc());
            operateTableMapper.createNewTable(stringObjectHashMap);
        }

        return result;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class}) //开启事务
    public int deleteById(int id) {
        int result = 0;
        //需要判断该表单对应的数据库表中是否有数据，有数据删除需要谨慎！
        Form form = formMapper.queryOne(id);
        //判断是否有表
        int ifCreateTable = operateTableMapper.existTable(form.getFormTableName());
        if (ifCreateTable == 1) { //如果有表，则需要判断是否有数据
            Map<String, Object> map = new HashMap<>();
            map.put("tableName", form.getFormTableName());
            int rowNumber = operateTableMapper.countTableRow(map); //获取表中数据行数
            if (rowNumber > 0) {
                result = -1; //返回-1表示有数据无法删除
            } else { //没有数据，可以直接删除，同时要删除表，删除相关字段
                fieldMapper.deleteOptionByFormId(form.getFormId()); //1、删除字段选项表中的数据
                fieldMapper.deleteByFormId(form.getFormId()); //2、删除表单相关字段的
                operateTableMapper.dropTable(map); //3、删除生成的相关联的动态表
                result = formMapper.deleteById(id); //4、删除该表单
            }
        } else { //如果没有表，也要清理一遍选项和字段
            fieldMapper.deleteOptionByFormId(form.getFormId()); //1、删除字段选项表中的数据
            fieldMapper.deleteByFormId(form.getFormId()); //2、删除表单相关字段的
            result = formMapper.deleteById(id); //删除表单
        }
        return result;
    }

    @Override
    public int update(Form obj) {
        return formMapper.update(obj);
    }

    @Override
    public Form queryOne(int id) {
        return formMapper.queryOne(id);
    }

    @Override
    public PageInfo<Form> queryAll(int page, int size) {
        PageHelper.startPage(page, size);
        List<Form> list = formMapper.queryAll();
        PageInfo<Form> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @Override
    public PageInfo<FormAndRows> queryFormAndRows(String searchValue, int page, int size) {
        PageHelper.startPage(page, size);
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("searchValue", searchValue);
        List<FormAndRows> list = formMapper.queryFormAndRows(paramMap);
        for (FormAndRows formAndRows : list) {
            System.out.println(formAndRows.toString());
            //获取字段个数
            int fieldNumber = fieldMapper.countFields(formAndRows.getFormId());
            formAndRows.setFieldNumber(fieldNumber);
            //判断是否有表
            int ifCreateTable = operateTableMapper.existTable(formAndRows.getFormTableName());
            formAndRows.setIfCreateTable(ifCreateTable);

            int rowNumber = 0;
            if (ifCreateTable > 0) {
                Map<String, Object> map = new HashMap<>();
                map.put("tableName", formAndRows.getFormTableName());
                rowNumber = operateTableMapper.countTableRow(map); //获取表中数据行数
            }
            formAndRows.setRowNumber(rowNumber);
        }
        PageInfo<FormAndRows> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }
}
