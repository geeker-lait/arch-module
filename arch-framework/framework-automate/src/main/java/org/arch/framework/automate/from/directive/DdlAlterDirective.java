package org.arch.framework.automate.from.directive;

import org.arch.framework.automate.from.directive.request.AlterTableRequest;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date 2/5/2021 5:26 PM
 */
public class DdlAlterDirective implements SqlDirective<AlterTableRequest> {
    @Override
    public <R extends DirectiveResponse> R exec(AlterTableRequest directiveRequest) {
        return null;
    }

    @Override
    public SqlDirectiveCode getSqlDirectiveCode() {
        return null;
    }
}
