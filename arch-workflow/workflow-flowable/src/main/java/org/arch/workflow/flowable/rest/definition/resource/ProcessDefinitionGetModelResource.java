package org.arch.workflow.flowable.rest.definition.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.io.IOUtils;
import org.arch.workflow.flowable.rest.model.ModelResponse;
import org.flowable.bpmn.converter.BpmnXMLConverter;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.common.engine.api.FlowableException;
import org.flowable.common.engine.api.FlowableObjectNotFoundException;
import org.flowable.editor.constants.ModelDataJsonConstants;
import org.flowable.editor.language.json.converter.BpmnJsonConverter;
import org.flowable.engine.ManagementService;
import org.flowable.engine.impl.cmd.GetDeploymentProcessDiagramCmd;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.Model;
import org.flowable.engine.repository.ProcessDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 流程定义获取对应模型接口
 *
 * @author lait.zhang@gmail.com
 * @date 2018/12/6
 */
@RestController
public class ProcessDefinitionGetModelResource extends BaseProcessDefinitionResource {

    private final ManagementService managementService;

    @Autowired
    public ProcessDefinitionGetModelResource(ManagementService managementService) {
        this.managementService = managementService;
    }

    @RequestMapping(value = "/process-definitions/{processDefinitionId}/getModel", method = RequestMethod.GET, produces = "application/json", name = "流程定义获取对应模型")
    public ModelResponse processDefinitionGetModel(@PathVariable String processDefinitionId) {
        ProcessDefinition processDefinition = getProcessDefinitionFromRequest(processDefinitionId);
        try {
            Deployment deployment = repositoryService.createDeploymentQuery().deploymentId(processDefinition.getDeploymentId()).singleResult();

            if (deployment == null) {
                throw new FlowableObjectNotFoundException("Could not find a process deployment with id '" + processDefinition.getDeploymentId() + "'.", Deployment.class);
            }
            Model modelData = null;

            if (deployment.getCategory() != null) {
                modelData = repositoryService.getModel(deployment.getCategory());
            }
            //如果不存在，创建对应模型
            if (modelData == null) {
                InputStream bpmnStream = repositoryService.getResourceAsStream(processDefinition.getDeploymentId(), processDefinition.getResourceName());
                XMLInputFactory xif = XMLInputFactory.newInstance();
                InputStreamReader in = new InputStreamReader(bpmnStream, "UTF-8");
                XMLStreamReader xtr = xif.createXMLStreamReader(in);
                BpmnModel bpmnModel = new BpmnXMLConverter().convertToBpmnModel(xtr);

                BpmnJsonConverter converter = new BpmnJsonConverter();
                ObjectNode modelNode = converter.convertToJson(bpmnModel);
                modelData = repositoryService.newModel();
                modelData.setKey(processDefinition.getKey());
                modelData.setName(processDefinition.getName());
                modelData.setCategory(processDefinition.getCategory());

                ObjectNode modelObjectNode = new ObjectMapper().createObjectNode();
                modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME, processDefinition.getName());
                modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, 1);
                modelObjectNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, processDefinition.getDescription());
                modelData.setMetaInfo(modelObjectNode.toString());

                repositoryService.saveModel(modelData);

                repositoryService.addModelEditorSource(modelData.getId(), modelNode.toString().getBytes("utf-8"));
                repositoryService.addModelEditorSourceExtra(modelData.getId(), IOUtils.toByteArray(managementService.executeCommand(new GetDeploymentProcessDiagramCmd(processDefinitionId))));

                repositoryService.setDeploymentCategory(processDefinition.getDeploymentId(), modelData.getId());
            }
            return restResponseFactory.createModelResponse(modelData);

        } catch (Exception e) {
            throw new FlowableException("Error  process-definition get model", e);
        }
    }
}
