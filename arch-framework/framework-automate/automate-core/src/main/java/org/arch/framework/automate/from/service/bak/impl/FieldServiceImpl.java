package org.arch.framework.automate.from.service.bak.impl;

import lombok.RequiredArgsConstructor;
import org.arch.framework.automate.from.entity.bak.Field;
import org.arch.framework.automate.from.entity.bak.FieldType;
import org.arch.framework.automate.from.entity.bak.Form;
import org.arch.framework.automate.from.entity.bak.Option;
import org.arch.framework.automate.from.mapper.bak.FieldMapper;
import org.arch.framework.automate.from.mapper.bak.FormMapper;
import org.arch.framework.automate.from.mapper.bak.OperateTableMapper;
import org.arch.framework.automate.from.service.bak.FieldService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class FieldServiceImpl implements FieldService {
    private final FieldMapper fieldMapper;
    private final FormMapper formMapper;
    private final OperateTableMapper operateTableMapper;

    @Override
    /**
     * 插入新字段后，自动生成并更新field_code，此处应该用事务
     */
    @Transactional
    public int insert(Field obj) {
        Integer checkResult = 0;
        //判断是否可以新增
        //先查询该字段所在的表单是否已经生成表
        //未生成表，可以新增
        //已生成表，数据为空，可以新增
        //其他不可新增
        checkResult = checkIfCouldHandle(obj.getFormId());
        if (checkResult == 1) {
            if (fieldMapper.countFieldByFieldNameAndFormId(obj) > 0) {
                System.out.println("字段名重复");
                return -2;//字段名称重复
            } else {
                System.out.println("新增开始");
                fieldMapper.insert(obj);
                System.out.println("新增完成");
                obj.setFieldCode("field" + obj.getFieldId());
                fieldMapper.updateFieldCode(obj);
                Form form1 = formMapper.queryOne(obj.getFormId());
                Form form = new Form();
                form.setFormDesc(obj.getFieldDesc());
                form.setFormName(form1.getFormTableName());
                form.setFormTableName(obj.getFieldCode());
                System.out.println(form.toString());
                formMapper.alertForm(form);
            }
        }
        return checkResult;
    }

    @Override
    public int deleteById(int id) {
        Integer checkResult = 0;
        //判断是否可以删除
        Field field = fieldMapper.queryOne(id);
        //先查询该字段所在的表单是否已经生成表
        checkResult = checkIfCouldDelete(field.getFormId());
        if (checkResult > 0) {
            fieldMapper.deleteById(id);
            fieldMapper.deleteOptionByFieldId(id);
            //当删除最后一个字段时，要同时删除动态的数据库表
            //先判断对应表单中的字段是否为空
            int countField = fieldMapper.countFields(field.getFormId());
            if (countField == 0 && checkResult == 2) { //当前字段为0个的时候，而且已经建表，drop table
                Form form = formMapper.queryOne(field.getFormId());
                Map<String, Object> tableNameMap = new HashMap<>();
                tableNameMap.put("tableName", form.getFormTableName());
                operateTableMapper.dropTable(tableNameMap);
            }
        }
        return checkResult;
    }

    @Override
    public int update(Field obj) {
        if (fieldMapper.countFieldByFieldNameAndFormId(obj) > 0) {
            return -2;//字段名称重复
        } else {
            return fieldMapper.update(obj);
        }
    }

    @Override
    public Field queryOne(int id) {
        return fieldMapper.queryOne(id);
    }

    @Override
    public List<Field> queryAll(Integer formId) {
        return fieldMapper.queryAll(formId);
    }

    @Override
    public List<Field> ifTableExistAndFieldList(Integer formId) {
        Form form = formMapper.queryOne(formId);
        int tableExist = operateTableMapper.existTable(form.getFormTableName());
        if (tableExist == 1) { //如果已经生成表
            return fieldMapper.queryAll(formId);
        } else {
            return null;
        }
    }

    @Override
    public List<FieldType> getFieldTypes() {
        return fieldMapper.getFieldTypes();
    }

    @Override
    public int insertOption(Option obj) {
        //选项名称不能重复 20190723
        if (fieldMapper.countOptionByOptionName(obj) > 0) {
            return -1;
        } else {
            return fieldMapper.insertOption(obj);
        }
    }

    @Override
    public int deleteOption(Integer optionId) {
        return fieldMapper.deleteOption(optionId);
    }

    @Override
    public List<Option> getOptionList(Integer fieldId) {
        return fieldMapper.getOptionList(fieldId);
    }

    @Override
    public int countFields(Integer formId) {
        return fieldMapper.countFields(formId);
    }

    private int checkIfCouldHandle(Integer formId) {
        //判断是否可以新增
        //先查询该字段所在的表单是否已经生成表
        //未生成表，可以新增
        //已生成表，数据为空，可以新增
        //其他不可新增
        int checkResult = 0;
        Form form = formMapper.queryOne(formId);
        int tableExist = operateTableMapper.existTable(form.getFormTableName());
        if (tableExist == 1) { //如果已经生成表
            Map<String, Object> map = new HashMap<>();
            map.put("tableName", form.getFormTableName());
            int tableRows = operateTableMapper.countTableRow(map);
            if (tableRows <= 0) {
                //表中数据行为0，也可以删除
                checkResult = 1;
            } else {
                checkResult = -1; //表中有数据，不可以删除！
            }
        } else {
            //如果没有生成表，可以删除
            checkResult = 1;
        }
        return checkResult;
    }

    /**
     * 0：不可删除字段。 1：动态表单未在数据库中建表，可删除。2：动态表单已建表，单无数据，可删除。
     *
     * @param formId
     * @return
     */
    private int checkIfCouldDelete(Integer formId) {
        int checkResult = 0;
        Form form = formMapper.queryOne(formId);
        int tableExist = operateTableMapper.existTable(form.getFormTableName());
        if (tableExist == 1) { //如果已经生成表
            Map<String, Object> map = new HashMap<>();
            map.put("tableName", form.getFormTableName());
            int tableRows = operateTableMapper.countTableRow(map);
            if (tableRows <= 0) {
                //表中数据行为0，也可以删除
                checkResult = 2;
            } else {
                checkResult = -1;
            }
        } else {
            //如果没有生成表，可以删除
            checkResult = 1;
        }
        return checkResult;
    }
}
