package org.arch.workflow.flowable.rest.definition.resource;

import org.arch.workflow.common.resource.BaseResource;
import org.arch.workflow.flowable.constant.ErrorConstant;
import org.arch.workflow.flowable.rest.RestResponseFactory;
import org.flowable.engine.FormService;
import org.flowable.engine.ManagementService;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.repository.ProcessDefinition;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 流程定义接口基类
 *
 * @author lait.zhang@gmail.com
 * @date 2018年4月17日
 */
public class BaseProcessDefinitionResource extends BaseResource {
    @Autowired
    protected RestResponseFactory restResponseFactory;
    @Autowired
    protected RepositoryService repositoryService;
    @Autowired
    protected ManagementService managementService;
    @Autowired
    protected RuntimeService runtimeService;
    @Autowired
    protected FormService formService;

    ProcessDefinition getProcessDefinitionFromRequest(String processDefinitionId) {
        // 直接查询数据库，不查询缓存，防止出现挂起激活验证不一致
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId).singleResult();

        if (processDefinition == null) {
            exceptionFactory.throwObjectNotFound(ErrorConstant.DEFINITION_NOT_FOUND, processDefinitionId);
        }

        return processDefinition;
    }
}
