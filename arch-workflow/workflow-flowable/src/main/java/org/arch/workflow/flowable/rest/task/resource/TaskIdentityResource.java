package org.arch.workflow.flowable.rest.task.resource;

import org.arch.workflow.flowable.constant.ErrorConstant;
import org.arch.workflow.flowable.constant.TableConstant;
import org.arch.workflow.flowable.rest.common.IdentityRequest;
import org.arch.workflow.flowable.rest.common.IdentityResponse;
import org.flowable.identitylink.api.IdentityLink;
import org.flowable.identitylink.api.history.HistoricIdentityLink;
import org.flowable.identitylink.service.IdentityLinkType;
import org.flowable.task.api.Task;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 任务候选信息接口
 *
 * @author lait.zhang@gmail.com
 * @date 2018年4月23日
 */
@RestController
public class TaskIdentityResource extends BaseTaskResource {

    @GetMapping(value = "/tasks/{taskId}/identity-links", name = "任务候选信息查询")
    public List<IdentityResponse> getIdentityLinks(@PathVariable String taskId) {
        HistoricTaskInstance task = getHistoricTaskFromRequest(taskId);
        List<HistoricIdentityLink> historicIdentityLinks = historyService.getHistoricIdentityLinksForTask(task.getId());
        return restResponseFactory.createTaskIdentityResponseList(historicIdentityLinks);
    }

    @PostMapping(value = "/tasks/{taskId}/identity-links", name = "任务候选信息创建")
    @ResponseStatus(value = HttpStatus.OK)
    public void createIdentityLink(@PathVariable String taskId, @RequestBody IdentityRequest taskIdentityRequest) {
        Task task = getTaskFromRequest(taskId);

        validateIdentityLinkArguments(taskIdentityRequest.getIdentityId(), taskIdentityRequest.getType());

        if (TableConstant.IDENTITY_GROUP.equals(taskIdentityRequest.getType())) {
            taskService.addGroupIdentityLink(task.getId(), taskIdentityRequest.getIdentityId(), IdentityLinkType.CANDIDATE);
        } else if (TableConstant.IDENTITY_USER.equals(taskIdentityRequest.getType())) {
            taskService.addUserIdentityLink(task.getId(), taskIdentityRequest.getIdentityId(), IdentityLinkType.CANDIDATE);
        }
    }

    @DeleteMapping(value = "/tasks/{taskId}/identity-links/{type}/{identityId}", name = "任务候选信息删除")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteIdentityLink(@PathVariable("taskId") String taskId, @PathVariable("identityId") String identityId, @PathVariable("type") String type) {
        Task task = getTaskFromRequest(taskId);

        validateIdentityLinkArguments(identityId, type);

        validateIdentityExists(taskId, identityId, type);

        if (TableConstant.IDENTITY_GROUP.equals(type)) {
            taskService.deleteGroupIdentityLink(task.getId(), identityId, IdentityLinkType.CANDIDATE);
        } else if (TableConstant.IDENTITY_USER.equals(type)) {
            taskService.deleteUserIdentityLink(task.getId(), identityId, IdentityLinkType.CANDIDATE);
        }
    }

    private void validateIdentityLinkArguments(String identityId, String type) {
        if (identityId == null) {
            exceptionFactory.throwIllegalArgument(ErrorConstant.TASK_IDENTITY_ID_NOT_FOUND);
        }
        if (type == null) {
            exceptionFactory.throwIllegalArgument(ErrorConstant.TASK_IDENTITY_TYPE_NOT_FOUND);
        }

        if (!TableConstant.IDENTITY_GROUP.equals(type) && !TableConstant.IDENTITY_USER.equals(type)) {
            exceptionFactory.throwIllegalArgument(ErrorConstant.TASK_IDENTITY_TYPE_ERROR);
        }
    }

    private void validateIdentityExists(String taskId, String identityId, String type) {
        List<IdentityLink> allLinks = taskService.getIdentityLinksForTask(taskId);
        for (IdentityLink link : allLinks) {
            boolean rightIdentity = false;
            if (TableConstant.IDENTITY_USER.equals(type)) {
                rightIdentity = identityId.equals(link.getUserId());
            } else {
                rightIdentity = identityId.equals(link.getGroupId());
            }

            if (rightIdentity && link.getType().equals(IdentityLinkType.CANDIDATE)) {
                return;
            }
        }
        exceptionFactory.throwObjectNotFound(ErrorConstant.TASK_IDENTITY_NOT_FOUND);
    }

}
