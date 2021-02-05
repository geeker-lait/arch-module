package org.arch.framework.automate.from.directive;

import lombok.RequiredArgsConstructor;
import org.arch.framework.automate.from.directive.request.CreateDatabaseRequest;
import org.arch.framework.automate.from.directive.request.CreateTableRequest;
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
public class DdlCreateTbDirective implements SqlDirective<CreateTableRequest> {

    private final FormDefinitionService formDefinitionService;

    @Override
    public <R extends DirectiveResponse> R exec(CreateTableRequest directiveRequest) {

        //formDefinitionService.
        return null;
    }

    @Override
    public SqlDirectiveCode getSqlDirectiveCode() {
        return SqlDirectiveCode.DDL_CREATE_TB;
    }
}
