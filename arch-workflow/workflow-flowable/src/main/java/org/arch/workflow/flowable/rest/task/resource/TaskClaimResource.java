package org.arch.workflow.flowable.rest.task.resource;

import org.arch.workflow.common.model.Authentication;
import org.flowable.task.api.Task;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * 任务认领接口
 *
 * @author lait.zhang@gmail.com
 * @date 2018年4月23日
 */
@RestController
public class TaskClaimResource extends BaseTaskResource {

    @PutMapping(value = "/tasks/{taskId}/claim", name = "任务认领")
    @ResponseStatus(value = HttpStatus.OK)
    public void claimTask(@PathVariable("taskId") String taskId) {
        Task task = getTaskFromRequest(taskId);
        taskService.claim(task.getId(), Authentication.getUserId());
    }
}
