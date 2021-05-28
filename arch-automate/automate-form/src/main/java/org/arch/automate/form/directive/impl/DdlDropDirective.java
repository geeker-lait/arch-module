package org.arch.automate.form.directive.impl;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.arch.framework.automate.api.dto.DirectiveRequestDto;
import org.arch.framework.automate.api.response.AlterTableResponse;
import org.arch.automate.common.configuration.DatabaseConfiguration;
import org.arch.automate.form.ddl.DDLOperate;
import org.arch.automate.form.directive.SqlDirective;
import org.arch.automate.form.directive.SqlDirectiveCode;
import org.arch.automate.form.mapper.DDLMapper;
import org.arch.automate.form.utils.DefinitionTableUtil;
import org.springframework.beans.BeanUtils;
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
        DatabaseConfiguration properties = null;
        if (directiveRequest.getDataSource() != null) {
            properties = new DatabaseConfiguration();
            BeanUtils.copyProperties(directiveRequest.getDataSource(), properties);
        }
        DDLOperate ddlOperate = DDLOperate.selectDDLOperate(properties);
        ddlOperate.dropTable(properties, DefinitionTableUtil.camelToUnderscore(directiveRequest.getDatabaseName()), DefinitionTableUtil.camelToUnderscore(directiveRequest.getTableName()));
        return null;
    }

    @Override
    public SqlDirectiveCode getSqlDirectiveCode() {
        return SqlDirectiveCode.DDL_DROP;
    }
}
