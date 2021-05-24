package org.arch.workflow.flowable.rest.definition.resource;

import org.arch.workflow.flowable.constant.ErrorConstant;
import org.arch.workflow.flowable.rest.definition.ProcessDefinitionActionRequest;
import org.flowable.engine.repository.ProcessDefinition;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * 流程定义挂起接口
 *
 * @author lait.zhang@gmail.com
 * @date 2018年4月17日
 */
@RestController
public class ProcessDefinitionSuspendResource extends BaseProcessDefinitionResource {

    @PutMapping(value = "/process-definitions/{processDefinitionId}/suspend", name = "流程定义挂起")
    @ResponseStatus(value = HttpStatus.OK)
    public void suspendProcessDefinition(@PathVariable String processDefinitionId, @RequestBody(required = false) ProcessDefinitionActionRequest actionRequest) {

        ProcessDefinition processDefinition = getProcessDefinitionFromRequest(processDefinitionId);

        if (processDefinition.isSuspended()) {
            exceptionFactory.throwConflict(ErrorConstant.DEFINITION_ALREADY_SUSPEND, processDefinition.getId());
        }

        if (actionRequest == null) {
            repositoryService.suspendProcessDefinitionById(processDefinitionId);
        } else {
            repositoryService.suspendProcessDefinitionById(processDefinition.getId(), actionRequest.isIncludeProcessInstances(), actionRequest.getDate());
        }

    }
}
