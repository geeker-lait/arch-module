package org.arch.workflow.flowable.rest.model.resource;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.arch.workflow.common.constant.CoreConstant;
import org.arch.workflow.common.utils.ObjectUtils;
import org.arch.workflow.flowable.cmd.SaveModelEditorCmd;
import org.arch.workflow.flowable.cmd.UpdateModelKeyCmd;
import org.arch.workflow.flowable.constant.ErrorConstant;
import org.arch.workflow.flowable.constant.TableConstant;
import org.arch.workflow.flowable.rest.model.ModelEditorJsonRequest;
import org.flowable.engine.repository.Model;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

/**
 * 模型设计器接口
 *
 * @author lait.zhang@gmail.com
 * @date 2018年4月11日
 */
@RestController
public class ModelEditorResource extends BaseModelResource {

    @GetMapping(value = "/models/{modelId}/editor", name = "设计器获取模型信息")
    public ObjectNode getEditorJson(@PathVariable String modelId) {
        ObjectNode modelNode = null;
        Model model = getModelFromRequest(modelId);
        try {
            if (ObjectUtils.isNotEmpty(model.getMetaInfo())) {
                modelNode = (ObjectNode) objectMapper.readTree(model.getMetaInfo());
            } else {
                modelNode = objectMapper.createObjectNode();
                modelNode.put("name", model.getName());
            }
            modelNode.put("key", model.getKey());
            modelNode.put("category", model.getCategory());
            modelNode.put("tenantId", model.getTenantId());
            modelNode.put("modelId", model.getId());
            byte[] editors = repositoryService.getModelEditorSource(model.getId());
            ObjectNode editorJsonNode = (ObjectNode) objectMapper.readTree(new String(editors, CoreConstant.DEFAULT_CHARSET));
            editorJsonNode.put("modelType", "model");
            modelNode.set("model", editorJsonNode);
        } catch (Exception e) {
            logger.error("获取模型设计信息异常", e);
            exceptionFactory.throwDefinedException(ErrorConstant.MODEL_GET_EDITOR_ERROR, e.getMessage());
        }
        return modelNode;
    }

    @PostMapping(value = "/models/{modelId}/editor", name = "模型设计器保存模型")
    @ResponseStatus(value = HttpStatus.OK)
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void saveModelEditor(@PathVariable String modelId, @RequestBody ModelEditorJsonRequest values) {
        Model model = getModel(modelId, values.isNewVersion());
        if (!model.getKey().equals(values.getKey())) {
            checkModelKeyExists(values.getKey());
            managementService.executeCommand(new UpdateModelKeyCmd(modelId, values.getKey()));
        }
        try {
            ObjectNode modelJson = (ObjectNode) objectMapper.readTree(model.getMetaInfo());
            modelJson.put("name", values.getName());
            modelJson.put("description", values.getDescription());
            model.setMetaInfo(modelJson.toString());
            model.setName(values.getName());
            model.setKey(values.getKey());
            repositoryService.saveModel(model);
            managementService.executeCommand(new SaveModelEditorCmd(model.getId(), values.getJsonXml()));
        } catch (Exception e) {
            logger.error("保存模型设计信息异常", e);
            exceptionFactory.throwDefinedException(ErrorConstant.MODEL_GET_EDITOR_ERROR, e.getMessage());
        }
    }

    private Model getModel(String modelId, boolean isNewVersion) {
        Model model = getModelFromRequest(modelId);
        if (isNewVersion) {
            Model newModel = repositoryService.newModel();
            Model lastModel = repositoryService.createModelQuery().modelKey(model.getKey()).latestVersion().singleResult();
            if (lastModel == null) {
                newModel.setVersion(TableConstant.MODEL_VESION_START);
            } else {
                newModel.setVersion(lastModel.getVersion() + 1);
            }
            newModel.setKey(model.getKey());
            newModel.setMetaInfo(model.getMetaInfo());
            newModel.setCategory(model.getCategory());
            newModel.setTenantId(model.getTenantId());
            return newModel;
        } else {
            return model;
        }
    }
}