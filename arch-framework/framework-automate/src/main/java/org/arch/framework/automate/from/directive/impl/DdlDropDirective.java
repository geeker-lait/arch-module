package org.arch.framework.automate.from.directive.impl;

import org.arch.framework.automate.from.directive.SqlDirective;
import org.arch.framework.automate.from.directive.SqlDirectiveCode;
import org.arch.framework.automate.from.directive.request.AlterTableRequest;
import org.arch.framework.automate.from.directive.response.AlterTableResponse;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date 2/5/2021 5:26 PM
 */
public class DdlDropDirective extends AbstractDirective implements SqlDirective<AlterTableRequest> {
    @Override
    public AlterTableResponse exec(AlterTableRequest directiveRequest) {
        return null;
    }

    @Override
    public SqlDirectiveCode getSqlDirectiveCode() {
        return null;
    }
}
