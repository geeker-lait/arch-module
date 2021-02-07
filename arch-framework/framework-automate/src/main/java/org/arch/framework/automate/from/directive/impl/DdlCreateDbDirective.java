package org.arch.framework.automate.from.directive.impl;

import org.arch.framework.automate.from.directive.SqlDirective;
import org.arch.framework.automate.from.directive.SqlDirectiveCode;
import org.arch.framework.automate.from.directive.request.CreateDatabaseRequest;
import org.arch.framework.automate.from.directive.response.CreateDatabaseResponse;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date 2/5/2021 5:26 PM
 */
public class DdlCreateDbDirective extends AbstractDirective implements SqlDirective<CreateDatabaseRequest> {


    @Override
    public CreateDatabaseResponse exec(CreateDatabaseRequest directiveRequest) {
        return null;
    }

    @Override
    public SqlDirectiveCode getSqlDirectiveCode() {
        return SqlDirectiveCode.DDL_CREATE_DB;
    }
}
