package org.arch.framework.automate.from.directive.impl;

import lombok.RequiredArgsConstructor;
import org.arch.framework.automate.api.DirectiveResponse;
import org.arch.framework.automate.api.request.FormDefinitionRequest;
import org.arch.framework.automate.from.directive.SqlDirective;
import org.arch.framework.automate.from.directive.SqlDirectiveCode;
import org.arch.framework.automate.from.service.FormDefinitionService;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date 2/5/2021 5:26 PM
 */
@RequiredArgsConstructor
public class DdlCreateDbDirective extends AbstractDirective implements SqlDirective<FormDefinitionRequest> {
    private final FormDefinitionService formDefinitionService;


    @Override
    public <R extends DirectiveResponse> R exec(FormDefinitionRequest directiveRequest) {
        return null;
    }

    @Override
    public SqlDirectiveCode getSqlDirectiveCode() {
        return SqlDirectiveCode.DDL_CREATE_DB;
    }
}
