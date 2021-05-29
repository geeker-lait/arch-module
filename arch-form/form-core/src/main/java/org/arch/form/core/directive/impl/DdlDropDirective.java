package org.arch.form.core.directive.impl;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.arch.form.api.dto.DirectiveRequestDto;
import org.arch.form.api.response.AlterTableResponse;
import org.arch.form.core.properties.DatabaseProperties;
import org.arch.form.core.ddl.DDLOperate;
import org.arch.form.core.directive.SqlDirective;
import org.arch.form.core.directive.SqlDirectiveCode;
import org.arch.form.core.utils.DefinitionTableUtil;
import org.arch.form.crud.mapper.DDLMapper;
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
        DatabaseProperties properties = null;
        if (directiveRequest.getDataSource() != null) {
            properties = new DatabaseProperties();
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
