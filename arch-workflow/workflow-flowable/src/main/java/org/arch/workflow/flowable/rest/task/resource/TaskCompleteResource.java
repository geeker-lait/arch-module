package org.arch.workflow.flowable.rest.task.resource;

import org.arch.workflow.common.model.Authentication;
import org.arch.workflow.flowable.constant.ErrorConstant;
import org.arch.workflow.flowable.rest.task.TaskCompleteRequest;
import org.arch.workflow.flowable.rest.variable.RestVariable;
import org.flowable.task.api.DelegationState;
import org.flowable.task.api.Task;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 任务完成接口
 *
 * @author lait.zhang@gmail.com
 * @date 2018/12/6
 */
@RestController
public class TaskCompleteResource extends BaseTaskResource {

    @PutMapping(value = "/tasks/{taskId}/complete", name = "任务完成")
    @ResponseStatus(value = HttpStatus.OK)
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void completeTask(@PathVariable String taskId, @RequestBody(required = false) TaskCompleteRequest taskCompleteRequest) {
        Task task = getTaskFromRequest(taskId);

        if (task.getAssignee() == null) {
            taskService.setAssignee(taskId, Authentication.getUserId());
        }

        Map<String, Object> completeVariables = new HashMap<>(8);
        if (taskCompleteRequest != null && taskCompleteRequest.getVariables() != null) {
            for (RestVariable variable : taskCompleteRequest.getVariables()) {
                if (variable.getName() == null) {
                    exceptionFactory.throwIllegalArgument(ErrorConstant.PARAM_NOT_FOUND, "变量名称");
                }
                completeVariables.put(variable.getName(), restResponseFactory.getVariableValue(variable));
            }
        }

        // 判断是否是协办完成还是正常流转
        if (task.getDelegationState() != null && task.getDelegationState().equals(DelegationState.PENDING)) {
            if (completeVariables.isEmpty()) {
                taskService.resolveTask(taskId);
            } else {
                taskService.resolveTask(taskId, completeVariables);
            }
        } else {
            if (completeVariables.isEmpty()) {
                taskService.complete(taskId);
            } else {
                taskService.complete(taskId, completeVariables);
            }
        }
    }

}
