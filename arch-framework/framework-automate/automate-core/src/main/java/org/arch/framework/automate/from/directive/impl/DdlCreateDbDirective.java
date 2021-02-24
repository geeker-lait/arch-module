package org.arch.framework.automate.from.directive.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.arch.framework.automate.api.DirectiveResponse;
import org.arch.framework.automate.api.dto.DirectiveRequestDto;
import org.arch.framework.automate.from.directive.SqlDirective;
import org.arch.framework.automate.from.directive.SqlDirectiveCode;
import org.arch.framework.automate.from.mapper.DDLMapper;
import org.arch.framework.automate.from.utils.DefinitionTableUtil;
import org.springframework.stereotype.Service;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date 2/5/2021 5:26 PM
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DdlCreateDbDirective extends AbstractDirective implements SqlDirective<DirectiveRequestDto> {
    private final DDLMapper ddlMapper;


    @Override
    public <R extends DirectiveResponse> R exec(DirectiveRequestDto directiveRequest) {
        String databaseName = DefinitionTableUtil.camelToUnderscore(directiveRequest.getDatabaseName());
        if (StringUtils.isBlank(databaseName)) {
            log.info("database name is null");
            return null;
        }
        if (ddlMapper.existDatabase(databaseName) > 0) {
            log.info("database exist dbName:{}", databaseName);
            return null;
        }
        ddlMapper.createDatabase(databaseName);
        return null;
    }

    @Override
    public SqlDirectiveCode getSqlDirectiveCode() {
        return SqlDirectiveCode.DDL_CREATE_DB;
    }
}
