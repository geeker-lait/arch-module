package org.arch.framework.automate.from.directive.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.arch.framework.automate.api.DirectiveResponse;
import org.arch.framework.automate.api.dto.DirectiveRequestDto;
import org.arch.framework.automate.from.ddl.DDLOperate;
import org.arch.framework.automate.from.directive.SqlDirective;
import org.arch.framework.automate.from.directive.SqlDirectiveCode;
import org.arch.framework.automate.from.utils.DefinitionTableUtil;
import org.arch.framework.automate.generater.properties.DatabaseProperties;
import org.springframework.beans.BeanUtils;
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

    @Override
    public <R extends DirectiveResponse> R exec(DirectiveRequestDto directiveRequest) {
        String databaseName = DefinitionTableUtil.camelToUnderscore(directiveRequest.getDatabaseName());
        if (StringUtils.isBlank(databaseName)) {
            log.info("database name is null");
            return null;
        }
        DatabaseProperties properties = null;
        if (directiveRequest.getDataSource() != null) {
            properties = new DatabaseProperties();
            BeanUtils.copyProperties(directiveRequest.getDataSource(), properties);
        }
        DDLOperate ddlOperate = DDLOperate.selectDDLOperate(properties);
        ddlOperate.createDatabase(properties, databaseName);
        return null;
    }

    @Override
    public SqlDirectiveCode getSqlDirectiveCode() {
        return SqlDirectiveCode.DDL_CREATE_DB;
    }
}
