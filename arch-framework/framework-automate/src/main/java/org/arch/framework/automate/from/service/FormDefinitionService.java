package org.arch.framework.automate.from.service;


import lombok.RequiredArgsConstructor;
import org.arch.framework.automate.from.entity.FormDefinition;
import org.arch.framework.automate.from.mapper.FormDefinitionMapper;
import org.arch.framework.crud.CrudService;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 表单定义服务接口
 *
 * @author lait
 * @since 2021-01-18 20:28:43
 * @description PN15855012581
 */
@RequiredArgsConstructor
@Service
public class FormDefinitionService {
    private final FormDefinitionMapper formDefinitionMapper;

    public Integer existTable(String tableName) {
        //return formDefinitionMapper.existTable(tableName);
        return null;
    }

    public Integer countTableRow(Map<String, Object> map) {
        return formDefinitionMapper.insert(null);
    }

    public Integer dropTable(Map<String, Object> map) {
        return formDefinitionMapper.dropTable("");
    }

    public Integer createNewTable(Map<String, Object> map) {
        formDefinitionMapper.createNewTable(map);
        return null;
    }
}
