package org.arch.framework.automate.from.directive;

import org.arch.framework.automate.from.directive.request.CreateDatabaseRequest;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date 2/5/2021 5:26 PM
 */
public class DdlCreateDbDirective implements SqlDirective<CreateDatabaseRequest> {


    @Override
    public <R extends DirectiveResponse> R exec(CreateDatabaseRequest directiveRequest) {
        return null;
    }

    @Override
    public SqlDirectiveCode getSqlDirectiveCode() {
        return SqlDirectiveCode.DDL_CREATE_DB;
    }
}
