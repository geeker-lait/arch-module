package org.arch.workflow.flowable.rest.definition.resource;

import org.flowable.job.api.Job;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 流程定义工作接口
 *
 * @author lait.zhang@gmail.com
 * @date 2018/12/6
 */
@RestController
public class ProcessDefinitionJobResource extends BaseProcessDefinitionResource {

    @GetMapping(value = "/process-definitions/{processDefinitionId}/jobs", name = "获取流程定义定时任务")
    @ResponseStatus(value = HttpStatus.OK)
    public List<Job> activateProcessDefinition(@PathVariable String processDefinitionId) {
        return managementService.createTimerJobQuery().processDefinitionId(processDefinitionId).list();
    }

    @DeleteMapping(value = "/process-definitions/{processDefinitionId}/jobs/{jobId}", name = "删除流程定义定时任务")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteJob(@PathVariable String processDefinitionId, @PathVariable String jobId) {
        managementService.deleteTimerJob(jobId);
    }
}
