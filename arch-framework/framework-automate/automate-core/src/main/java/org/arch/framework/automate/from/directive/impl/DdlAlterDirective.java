package org.arch.framework.automate.from.directive.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.arch.framework.automate.api.dto.DirectiveRequestDto;
import org.arch.framework.automate.api.response.AlterTableResponse;
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
public class DdlAlterDirective extends AbstractDirective implements SqlDirective<DirectiveRequestDto> {
    private final FormDefinitionService formDefinitionService;

    @Override
    public AlterTableResponse exec(DirectiveRequestDto directiveRequest) {
        String databaseName = DefinitionTableUtil.camelToUnderscore(directiveRequest.getDatabaseName());
        String tableName = DefinitionTableUtil.camelToUnderscore(directiveRequest.getTableName());
//        DatabaseProperties properties = null;
//        if (directiveRequest.getDataSource() != null) {
//            properties = new DatabaseProperties();
//            BeanUtils.copyProperties(directiveRequest.getDataSource(), properties);
//        }
//        DDLOperate ddlOperate = DDLOperate.selectDDLOperate(properties);
        if (StringUtils.isBlank(tableName) || StringUtils.isBlank(databaseName)) {
            return null;
        }
        Map<String, Object> searchParams = new HashMap<>(4);
        searchParams.put("table_name", tableName);
        IPage<FormDefinition> searchResult = formDefinitionService.findPageBySort(searchParams, 1, 2, Direction.DESC, "ver", "id");
        if (searchResult.getTotal() < 2) {
            log.info("table formDefinition data quantity error, need two pieces of data, don't exec, tableName:{} databaseName:{}", tableName, databaseName);
            return null;
        }
        String latestVersionJson = searchResult.getRecords().get(0).getDefinitionJson();
        // 修改表 时先 drop table 然后 create
//        ddlOperate.dropTable(properties, databaseName, tableName);
//        DefinitionTableDto definitionTableDto = DefinitionTableUtil.buildCreateParams(JSONObject.parseObject(latestVersionJson, FormDefinitionJsonDto.class));
//        if (definitionTableDto == null) {
//            return null;
//        }
//        ddlOperate.createTable(properties, definitionTableDto);
        return null;
    }

    @Override
    public SqlDirectiveCode getSqlDirectiveCode() {
        return null;
    }
}
