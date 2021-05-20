package org.arch.workflow.flowable.rest.task.resource;

import org.arch.workflow.common.resource.BaseResource;
import org.arch.workflow.flowable.constant.ErrorConstant;
import org.arch.workflow.flowable.rest.RestResponseFactory;
import org.flowable.engine.FormService;
import org.flowable.engine.HistoryService;
import org.flowable.engine.TaskService;
import org.flowable.task.api.Task;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 任务接口基类
 *
 * @author lait.zhang@gmail.com
 * @date 2018/12/6
 */
public class BaseTaskResource extends BaseResource {
    @Autowired
    protected RestResponseFactory restResponseFactory;
    @Autowired
    protected TaskService taskService;
    @Autowired
    protected HistoryService historyService;
    @Autowired
    protected FormService formService;

    Task getTaskFromRequest(String taskId) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        if (task == null) {
            exceptionFactory.throwObjectNotFound(ErrorConstant.TASK_NOT_FOUND, taskId);
        }
        return task;
    }

    HistoricTaskInstance getHistoricTaskFromRequest(String taskId) {
        HistoricTaskInstance task = historyService.createHistoricTaskInstanceQuery().taskId(taskId).singleResult();
        if (task == null) {
            exceptionFactory.throwObjectNotFound(ErrorConstant.TASK_NOT_FOUND, taskId);
        }
        return task;
    }

}
