package org.arch.workflow.flowable.rest.instance.resource;

import org.arch.workflow.common.resource.BaseResource;
import org.arch.workflow.flowable.constant.ErrorConstant;
import org.arch.workflow.flowable.rest.RestResponseFactory;
import org.flowable.engine.HistoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 流程实例基础接口
 *
 * @author lait.zhang@gmail.com
 * @date 2018/12/6
 */
public class BaseProcessInstanceResource extends BaseResource {
    @Autowired
    protected RestResponseFactory restResponseFactory;
    @Autowired
    protected HistoryService historyService;
    @Autowired
    protected RuntimeService runtimeService;
    @Autowired
    protected TaskService taskService;

    ProcessInstance getProcessInstanceFromRequest(String processInstanceId) {
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        if (processInstance == null) {
            exceptionFactory.throwObjectNotFound(ErrorConstant.INSTANCE_NOT_FOUND, processInstanceId);
        }
        return processInstance;
    }

    HistoricProcessInstance getHistoricProcessInstanceFromRequest(String processInstanceId) {
        HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        if (historicProcessInstance == null) {
            exceptionFactory.throwObjectNotFound(ErrorConstant.INSTANCE_NOT_FOUND, processInstanceId);
        }
        return historicProcessInstance;
    }
}
