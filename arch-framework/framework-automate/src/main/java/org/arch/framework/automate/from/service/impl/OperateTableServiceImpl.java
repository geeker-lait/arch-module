package org.arch.framework.automate.from.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import org.arch.framework.automate.from.entity.FieldAndOptionDetail;
import org.arch.framework.automate.from.entity.Option;
import org.arch.framework.automate.from.mapper.FieldMapper;
import org.arch.framework.automate.from.mapper.OperateTableMapper;
import org.arch.framework.automate.from.service.OperateTableService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class OperateTableServiceImpl implements OperateTableService {
    private final OperateTableMapper operateTableMapper;
    private final FieldMapper fieldMapper;

    @Override
    public Integer existTable(String tableName) {
        return operateTableMapper.existTable(tableName);
    }

    @Override
    public Integer countTableRow(Map<String, Object> map) {
        return operateTableMapper.countTableRow(map);
    }

    @Override
    public Integer dropTable(Map<String, Object> map) {
        return operateTableMapper.dropTable(map);
    }

    @Override
    public Integer createNewTable(Map<String, Object> map) {
        return operateTableMapper.createNewTable(map);
    }

    @Override
    public List<String> getFieldList(Integer formId) {
        return operateTableMapper.getFieldList(formId);
    }

    @Override
    public PageInfo<Map<String, Object>> queryAll(Map<String, Object> map, int page, int size) {
        PageHelper.startPage(page, size);
        // 查询表单之前，先确认动态表是否存在
        int existTable = operateTableMapper.existTable((String) map.get("tableName"));
        List<Map<String, Object>> list = new ArrayList<>();
        if (existTable == 1) {
            list = operateTableMapper.queryAll(map);
        }
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @Override
    public List<FieldAndOptionDetail> getFieldAndOptionDetailList(Integer formId) {
        List<FieldAndOptionDetail> list = operateTableMapper.getFieldAndOptionDetailList(formId);
        for (FieldAndOptionDetail field : list) {
            List<Option> optionList = fieldMapper.getOptionList(field.getFieldId());
            field.setOptionList(optionList);
        }
        return list;
    }

    @Override
    public Integer insertTable(Map<String, Object> map) {
        return operateTableMapper.insertTable(map);
    }

    @Override
    public Integer updateTable(Map<String, Object> map) {
        return operateTableMapper.updateTable(map);
    }

    @Override
    public Integer deleteTable(Map<String, Object> map) {
        return operateTableMapper.deleteTable(map);
    }

    @Override
    public List<Map<String, Object>> queryOne(Map<String, Object> map) {
        return operateTableMapper.queryOne(map);
    }
}
