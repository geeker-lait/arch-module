package org.arch.framework.automate.from.directive.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.arch.framework.automate.api.dto.DefinitionTableDto;
import org.arch.framework.automate.api.dto.DirectiveRequestDto;
import org.arch.framework.automate.api.dto.FormDefinitionJsonDto;
import org.arch.framework.automate.api.response.CreateTableResponse;
import org.arch.framework.automate.from.directive.SqlDirective;
import org.arch.framework.automate.from.directive.SqlDirectiveCode;
import org.arch.framework.automate.from.entity.FormDefinition;
import org.arch.framework.automate.from.service.FormDefinitionService;
import org.arch.framework.automate.from.utils.DefinitionTableUtil;
import org.arch.framework.crud.Direction;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date 2/5/2021 5:26 PM
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DdlCreateTbDirective extends AbstractDirective implements SqlDirective<DirectiveRequestDto> {

    private final FormDefinitionService formDefinitionService;

    @Override
    public CreateTableResponse exec(DirectiveRequestDto directiveRequest) {
        String databaseName = DefinitionTableUtil.camelToUnderscore(directiveRequest.getDatabaseName());
        String tableName = DefinitionTableUtil.camelToUnderscore(directiveRequest.getTableName());
        if (StringUtils.isBlank(databaseName) || StringUtils.isBlank(tableName)) {
            return null;
        }
//        DatabaseProperties properties = null;
//        if (directiveRequest.getDataSource() != null) {
//            properties = new DatabaseProperties();
//            BeanUtils.copyProperties(directiveRequest.getDataSource(), properties);
//        }
//        DDLOperate ddlOperate = DDLOperate.selectDDLOperate(properties);
        FormDefinition formDefinition = new FormDefinition();
        formDefinition.setTableName(tableName);
        // 根据table 查询 数据
        Map<String, Object> searchParams = new HashMap<>(4);
        searchParams.put("table_name", tableName);
        IPage<FormDefinition> searchResult = formDefinitionService.findPageBySort(searchParams, 1, 1, Direction.DESC, "ver", "id");
        if (searchResult.getTotal() != 1) {
            log.info("table formDefinition data quantity error, need one pieces of data, don't exec, tableName:{} databaseName:{}", tableName, databaseName);
            return null;
        }
        FormDefinitionJsonDto formDefinitionJsonDto = JSONObject.parseObject(searchResult.getRecords().get(0).getDefinitionJson(), FormDefinitionJsonDto.class);
        formDefinitionJsonDto.setName(databaseName);
        formDefinitionJsonDto.getTable().setName(tableName);
        DefinitionTableDto definitionTableDto = DefinitionTableUtil.buildCreateParams(formDefinitionJsonDto);
        if (definitionTableDto == null) {
            return null;
        }
//        ddlOperate.createTable(properties, definitionTableDto);
        return null;
    }

    @Override
    public SqlDirectiveCode getSqlDirectiveCode() {
        return SqlDirectiveCode.DDL_CREATE_TB;
    }
}
