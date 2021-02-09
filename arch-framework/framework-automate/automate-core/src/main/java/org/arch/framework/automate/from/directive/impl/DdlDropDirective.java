package org.arch.framework.automate.from.directive.impl;

import org.arch.framework.automate.api.request.FormDefinitionRequest;
import org.arch.framework.automate.api.response.AlterTableResponse;
import org.arch.framework.automate.from.directive.SqlDirective;
import org.arch.framework.automate.from.directive.SqlDirectiveCode;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date 2/5/2021 5:26 PM
 */
public class DdlDropDirective extends AbstractDirective implements SqlDirective<FormDefinitionRequest> {
    @Override
    public AlterTableResponse exec(FormDefinitionRequest directiveRequest) {
        return null;
    }

    @Override
    public SqlDirectiveCode getSqlDirectiveCode() {
        return null;
    }
}
