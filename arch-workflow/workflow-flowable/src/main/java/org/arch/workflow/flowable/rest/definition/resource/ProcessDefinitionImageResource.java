package org.arch.workflow.flowable.rest.definition.resource;

import org.apache.commons.io.IOUtils;
import org.arch.workflow.flowable.constant.ErrorConstant;
import org.flowable.engine.repository.ProcessDefinition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.InputStream;

/**
 * 流行定义图片接口
 *
 * @author lait.zhang@gmail.com
 * @date 2018/12/6
 */
@RestController
public class ProcessDefinitionImageResource extends BaseProcessDefinitionResource {

    @GetMapping(value = "/process-definitions/{processDefinitionId}/image", name = "流程定义流程图")
    public ResponseEntity<byte[]> getProcessDefinitionImage(@PathVariable String processDefinitionId) {
        ProcessDefinition processDefinition = getProcessDefinitionFromRequest(processDefinitionId);
        InputStream imageStream = repositoryService.getProcessDiagram(processDefinition.getId());

        if (imageStream == null) {
            exceptionFactory.throwObjectNotFound(ErrorConstant.DEFINITION_IMAGE_NOT_FOUND, processDefinition.getId());
        }
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.IMAGE_PNG);
        try {
            return new ResponseEntity<>(IOUtils.toByteArray(imageStream), responseHeaders, HttpStatus.OK);
        } catch (Exception e) {
            exceptionFactory.throwDefinedException(ErrorConstant.DEFINITION_IMAGE_READ_ERROR, e.getMessage());
        }
        return null;
    }

}
