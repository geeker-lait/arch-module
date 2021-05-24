package org.arch.workflow.flowable.rest.task.resource;

import org.arch.workflow.common.resource.PageResponse;
import org.arch.workflow.common.utils.ObjectUtils;
import org.arch.workflow.flowable.constant.ErrorConstant;
import org.arch.workflow.flowable.rest.task.TaskDetailResponse;
import org.arch.workflow.flowable.rest.task.TaskEditRequest;
import org.arch.workflow.flowable.rest.task.TaskPaginateList;
import org.arch.workflow.flowable.rest.task.TaskResponse;
import org.flowable.common.engine.api.query.QueryProperty;
import org.flowable.task.api.Task;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.flowable.task.api.history.HistoricTaskInstanceQuery;
import org.flowable.task.service.impl.HistoricTaskInstanceQueryProperty;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 任务基础接口
 *
 * @author lait.zhang@gmail.com
 * @date 2018/12/6
 */
@RestController
public class TaskResource extends BaseTaskResource {

    private static Map<String, QueryProperty> allowedSortProperties = new HashMap<>();

    static {
        allowedSortProperties.put("deleteReason", HistoricTaskInstanceQueryProperty.DELETE_REASON);
        allowedSortProperties.put("duration", HistoricTaskInstanceQueryProperty.DURATION);
        allowedSortProperties.put("endTime", HistoricTaskInstanceQueryProperty.END);
        allowedSortProperties.put("executionId", HistoricTaskInstanceQueryProperty.EXECUTION_ID);
        allowedSortProperties.put("taskInstanceId", HistoricTaskInstanceQueryProperty.HISTORIC_TASK_INSTANCE_ID);
        allowedSortProperties.put("processDefinitionId", HistoricTaskInstanceQueryProperty.PROCESS_DEFINITION_ID);
        allowedSortProperties.put("processInstanceId", HistoricTaskInstanceQueryProperty.PROCESS_INSTANCE_ID);
        allowedSortProperties.put("assignee", HistoricTaskInstanceQueryProperty.TASK_ASSIGNEE);
        allowedSortProperties.put("taskDefinitionKey", HistoricTaskInstanceQueryProperty.TASK_DEFINITION_KEY);
        allowedSortProperties.put("description", HistoricTaskInstanceQueryProperty.TASK_DESCRIPTION);
        allowedSortProperties.put("dueDate", HistoricTaskInstanceQueryProperty.TASK_DUE_DATE);
        allowedSortProperties.put("name", HistoricTaskInstanceQueryProperty.TASK_NAME);
        allowedSortProperties.put("owner", HistoricTaskInstanceQueryProperty.TASK_OWNER);
        allowedSortProperties.put("priority", HistoricTaskInstanceQueryProperty.TASK_PRIORITY);
        allowedSortProperties.put("tenantId", HistoricTaskInstanceQueryProperty.TENANT_ID_);
        allowedSortProperties.put("startTime", HistoricTaskInstanceQueryProperty.START);
    }

    @GetMapping(value = "/tasks", name = "任务查询")
    public PageResponse getTasks(@RequestParam Map<String, String> requestParams) {
        HistoricTaskInstanceQuery query = historyService.createHistoricTaskInstanceQuery();

        if (ObjectUtils.isNotEmpty(requestParams.get("taskId"))) {
            query.taskId(requestParams.get("taskId"));
        }
        if (ObjectUtils.isNotEmpty(requestParams.get("processInstanceId"))) {
            query.processInstanceId(requestParams.get("processInstanceId"));
        }
        if (ObjectUtils.isNotEmpty(requestParams.get("processInstanceBusinessKey"))) {
            query.processInstanceBusinessKeyLike(ObjectUtils.convertToLike(requestParams.get("processInstanceBusinessKey")));
        }
        if (ObjectUtils.isNotEmpty(requestParams.get("processDefinitionKey"))) {
            query.processDefinitionKeyLike(ObjectUtils.convertToLike(requestParams.get("processDefinitionKey")));
        }
        if (ObjectUtils.isNotEmpty(requestParams.get("processDefinitionId"))) {
            query.processDefinitionId(requestParams.get("processDefinitionId"));
        }
        if (ObjectUtils.isNotEmpty(requestParams.get("processDefinitionName"))) {
            query.processDefinitionNameLike(ObjectUtils.convertToLike(requestParams.get("processDefinitionName")));
        }
        if (ObjectUtils.isNotEmpty(requestParams.get("executionId"))) {
            query.executionId(requestParams.get("executionId"));
        }
        if (ObjectUtils.isNotEmpty(requestParams.get("taskName"))) {
            query.taskNameLike(ObjectUtils.convertToLike(requestParams.get("taskName")));
        }
        if (ObjectUtils.isNotEmpty(requestParams.get("taskDescription"))) {
            query.taskDescriptionLike(ObjectUtils.convertToLike(requestParams.get("taskDescription")));
        }
        if (ObjectUtils.isNotEmpty(requestParams.get("taskDefinitionKey"))) {
            query.taskDefinitionKeyLike(ObjectUtils.convertToLike(requestParams.get("taskDefinitionKey")));
        }
        if (ObjectUtils.isNotEmpty(requestParams.get("taskAssignee"))) {
            query.taskAssignee(requestParams.get("taskAssignee"));
        }
        if (ObjectUtils.isNotEmpty(requestParams.get("taskOwner"))) {
            query.taskOwner(requestParams.get("taskOwner"));
        }
        if (ObjectUtils.isNotEmpty(requestParams.get("taskInvolvedUser"))) {
            query.taskInvolvedUser(requestParams.get("taskInvolvedUser"));
        }
        if (ObjectUtils.isNotEmpty(requestParams.get("taskPriority"))) {
            query.taskPriority(ObjectUtils.convertToInteger(requestParams.get("taskPriority")));
        }
        if (ObjectUtils.isNotEmpty(requestParams.get("finished"))) {
            boolean isFinished = ObjectUtils.convertToBoolean(requestParams.get("finished"));
            if (isFinished) {
                query.finished();
            } else {
                query.unfinished();
            }
        }
        if (ObjectUtils.isNotEmpty(requestParams.get("processFinished"))) {
            boolean isProcessFinished = ObjectUtils.convertToBoolean(requestParams.get("processFinished"));
            if (isProcessFinished) {
                query.processFinished();
            } else {
                query.processUnfinished();
            }
        }
        if (ObjectUtils.isNotEmpty(requestParams.get("parentTaskId"))) {
            query.taskParentTaskId(requestParams.get("parentTaskId"));
        }
        if (ObjectUtils.isNotEmpty(requestParams.get("dueDateAfter"))) {
            query.taskDueAfter(ObjectUtils.convertToDatetime(requestParams.get("dueDateAfter")));
        }

        if (ObjectUtils.isNotEmpty(requestParams.get("dueDateBefore"))) {
            query.taskDueBefore(ObjectUtils.convertToDatetime(requestParams.get("dueDateBefore")));
        }
        if (ObjectUtils.isNotEmpty(requestParams.get("taskCreatedBefore"))) {
            query.taskCreatedBefore(ObjectUtils.convertToDatetime(requestParams.get("taskCreatedBefore")));
        }
        if (ObjectUtils.isNotEmpty(requestParams.get("taskCreatedAfter"))) {
            query.taskCreatedAfter(ObjectUtils.convertToDatetime(requestParams.get("taskCreatedAfter")));
        }
        if (ObjectUtils.isNotEmpty(requestParams.get("taskCompletedBefore"))) {
            query.taskCompletedBefore(ObjectUtils.convertToDatetime(requestParams.get("taskCompletedBefore")));
        }
        if (ObjectUtils.isNotEmpty(requestParams.get("taskCompletedAfter"))) {
            query.taskCompletedAfter(ObjectUtils.convertToDatetime(requestParams.get("taskCompletedAfter")));
        }
        if (ObjectUtils.isNotEmpty(requestParams.get("tenantId"))) {
            query.taskTenantId(requestParams.get("tenantId"));
        }

        if (ObjectUtils.isNotEmpty(requestParams.get("taskCandidateUser"))) {
            query.taskCandidateUser(requestParams.get("taskCandidateUser"));
        }
        if (ObjectUtils.isNotEmpty(requestParams.get("taskCandidateGroup"))) {
            query.taskCandidateGroup(requestParams.get("taskCandidateGroup"));
        }
        if (ObjectUtils.isNotEmpty(requestParams.get("taskCandidateGroups"))) {
            String[] candidateGroups = requestParams.get("taskCandidateGroups").split(",");
            query.taskCandidateGroupIn(Arrays.asList(candidateGroups));
        }
        return new TaskPaginateList(restResponseFactory).paginateList(getPageable(requestParams), query, allowedSortProperties);
    }

    @GetMapping(value = "/tasks/{taskId}", name = "根据ID任务查询")
    public TaskDetailResponse getTaskById(@PathVariable("taskId") String taskId) {
        HistoricTaskInstance historicTaskInstance = getHistoricTaskFromRequest(taskId);

        Task task = null;
        if (historicTaskInstance.getEndTime() == null) {
            task = getTaskFromRequest(taskId);
        }

        String formKey = formService.getTaskFormKey(historicTaskInstance.getProcessDefinitionId(), historicTaskInstance.getTaskDefinitionKey());

        return restResponseFactory.createTaskDetailResponse(historicTaskInstance, task, formKey);
    }

    @PutMapping(value = "/tasks/{taskId}", name = "任务修改")
    public TaskResponse updateTask(@PathVariable String taskId, @RequestBody TaskEditRequest taskEditRequest) {
        Task task = getTaskFromRequest(taskId);
        task.setName(taskEditRequest.getName());
        task.setDescription(taskEditRequest.getDescription());
        task.setAssignee(taskEditRequest.getAssignee());
        task.setOwner(taskEditRequest.getOwner());
        task.setDueDate(taskEditRequest.getDueDate());
        task.setPriority(taskEditRequest.getPriority());
        task.setCategory(taskEditRequest.getCategory());
        taskService.saveTask(task);
        return restResponseFactory.createTaskResponse(task);
    }

    @DeleteMapping(value = "/tasks/{taskId}", name = "任务删除")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteTask(@PathVariable String taskId) {
        HistoricTaskInstance task = getHistoricTaskFromRequest(taskId);
        if (task.getEndTime() == null) {
            exceptionFactory.throwForbidden(ErrorConstant.TASK_RUN_NOT_DELETE, taskId);
        }
        historyService.deleteHistoricTaskInstance(task.getId());
    }
}
