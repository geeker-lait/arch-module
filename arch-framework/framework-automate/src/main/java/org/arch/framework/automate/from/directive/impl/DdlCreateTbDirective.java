package org.arch.framework.automate.from.directive.impl;

import lombok.RequiredArgsConstructor;
import org.arch.framework.automate.from.directive.DirectiveResponse;
import org.arch.framework.automate.from.directive.SqlDirective;
import org.arch.framework.automate.from.directive.SqlDirectiveCode;
import org.arch.framework.automate.from.directive.request.CreateTableRequest;
import org.arch.framework.automate.from.directive.response.CreateTableResponse;
import org.arch.framework.automate.from.service.FormDefinitionService;
import org.springframework.stereotype.Service;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date 2/5/2021 5:26 PM
 */
@Service
@RequiredArgsConstructor
public class DdlCreateTbDirective extends AbstractDirective  implements SqlDirective<CreateTableRequest> {

    private final FormDefinitionService formDefinitionService;

    @Override
    public CreateTableResponse exec(CreateTableRequest directiveRequest) {

        //formDefinitionService.
        return null;
    }

    @Override
    public SqlDirectiveCode getSqlDirectiveCode() {
        return SqlDirectiveCode.DDL_CREATE_TB;
    }
}
