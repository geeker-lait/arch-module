package org.arch.automate.form.directive.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.arch.framework.automate.api.DirectiveResponse;
import org.arch.framework.automate.api.dto.DirectiveRequestDto;
import org.arch.automate.common.configuration.DatabaseConfiguration;
import org.arch.automate.form.ddl.DDLOperate;
import org.arch.automate.form.directive.SqlDirective;
import org.arch.automate.form.directive.SqlDirectiveCode;
import org.arch.automate.form.utils.DefinitionTableUtil;
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
        DatabaseConfiguration properties = null;
        if (directiveRequest.getDataSource() != null) {
            properties = new DatabaseConfiguration();
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
