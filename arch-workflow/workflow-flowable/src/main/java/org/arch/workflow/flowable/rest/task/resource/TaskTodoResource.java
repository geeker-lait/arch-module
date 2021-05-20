package org.arch.workflow.flowable.rest.task.resource;

import org.arch.workflow.common.model.Authentication;
import org.arch.workflow.common.resource.PageResponse;
import org.arch.workflow.common.utils.ObjectUtils;
import org.arch.workflow.flowable.rest.task.TaskTodoPaginateList;
import org.flowable.common.engine.api.query.QueryProperty;
import org.flowable.task.api.TaskQuery;
import org.flowable.task.service.impl.TaskQueryProperty;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 任务基础接口
 *
 * @author lait.zhang@gmail.com
 * @date 2018/12/6
 */
@RestController
public class TaskTodoResource extends BaseTaskResource {

    private static Map<String, QueryProperty> allowedSortProperties = new HashMap<>();

    static {
        allowedSortProperties.put("processDefinitionId", TaskQueryProperty.PROCESS_DEFINITION_ID);
        allowedSortProperties.put("processInstanceId", TaskQueryProperty.PROCESS_INSTANCE_ID);
        allowedSortProperties.put("taskDefinitionKey", TaskQueryProperty.TASK_DEFINITION_KEY);
        allowedSortProperties.put("dueDate", TaskQueryProperty.DUE_DATE);
        allowedSortProperties.put("name", TaskQueryProperty.NAME);
        allowedSortProperties.put("priority", TaskQueryProperty.PRIORITY);
        allowedSortProperties.put("tenantId", TaskQueryProperty.TENANT_ID);
        allowedSortProperties.put("createTime", TaskQueryProperty.CREATE_TIME);
    }

    @GetMapping(value = "/tasks/todo", name = "待办任务查询")
    public PageResponse getTasks(@RequestParam Map<String, String> requestParams) {
        TaskQuery query = taskService.createTaskQuery();

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
        if (ObjectUtils.isNotEmpty(requestParams.get("tenantId"))) {
            query.taskTenantId(requestParams.get("tenantId"));
        }

        if (ObjectUtils.isNotEmpty(requestParams.get("suspended"))) {
            boolean isSuspended = ObjectUtils.convertToBoolean(requestParams.get("suspended"));
            if (isSuspended) {
                query.suspended();
            } else {
                query.active();
            }
        }

        query.or().taskCandidateOrAssigned(Authentication.getUserId()).taskOwner(Authentication.getUserId()).endOr();

        return new TaskTodoPaginateList(restResponseFactory).paginateList(getPageable(requestParams), query, allowedSortProperties);
    }

}
