package org.arch.workflow.flowable.rest.model.resource;

import org.arch.workflow.flowable.cmd.DeployModelCmd;
import org.arch.workflow.flowable.rest.definition.ProcessDefinitionResponse;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.Model;
import org.flowable.engine.repository.ProcessDefinition;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * 模型部署接口
 *
 * @author lait.zhang@gmail.com
 * @date 2018/12/6
 */
@RestController
public class ModelDeployResource extends BaseModelResource {

    @PostMapping(value = "/models/{modelId}/deploy", name = "模型部署")
    @ResponseStatus(value = HttpStatus.CREATED)
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public ProcessDefinitionResponse deployModel(@PathVariable String modelId) {
        Model model = getModelFromRequest(modelId);
        Deployment deployment = managementService.executeCommand(new DeployModelCmd(model.getId()));

        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().deploymentId(deployment.getId()).singleResult();
        return restResponseFactory.createProcessDefinitionResponse(processDefinition);
    }
}
