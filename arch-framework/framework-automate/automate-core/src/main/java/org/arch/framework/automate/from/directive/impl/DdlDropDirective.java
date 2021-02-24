package org.arch.framework.automate.from.directive.impl;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.arch.framework.automate.api.dto.DirectiveRequestDto;
import org.arch.framework.automate.api.response.AlterTableResponse;
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
@Service
@RequiredArgsConstructor
public class DdlDropDirective extends AbstractDirective implements SqlDirective<DirectiveRequestDto> {
    private final DDLMapper ddlMapper;

    @Override
    public AlterTableResponse exec(DirectiveRequestDto directiveRequest) {
        if (StringUtils.isBlank(directiveRequest.getTableName()) || StringUtils.isBlank(directiveRequest.getDatabaseName())) {
            return null;
        }
        ddlMapper.dropTable(DefinitionTableUtil.camelToUnderscore(directiveRequest.getDatabaseName()), DefinitionTableUtil.camelToUnderscore(directiveRequest.getTableName()));
        return null;
    }

    @Override
    public SqlDirectiveCode getSqlDirectiveCode() {
        return SqlDirectiveCode.DDL_DROP;
    }
}
