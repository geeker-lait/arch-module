package org.arch.workflow.flowable.cmd;

import org.flowable.common.engine.impl.interceptor.Command;
import org.flowable.common.engine.impl.interceptor.CommandContext;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.impl.util.CommandContextUtil;
import org.flowable.engine.repository.Model;

import java.io.Serializable;
import java.util.List;


/**
 * 批量修改模型key
 *
 * @author lait.zhang@gmail.com
 * @date 2018年4月11日
 */
public class UpdateModelKeyCmd implements Command<Void>, Serializable {
    private static final long serialVersionUID = 1L;
    private String modelId;
    private String modelKey;

    public UpdateModelKeyCmd(String modelId, String modelKey) {
        this.modelId = modelId;
        this.modelKey = modelKey;
    }

    @Override
    public Void execute(CommandContext commandContext) {
        RepositoryService repositoryService = CommandContextUtil.getProcessEngineConfiguration(commandContext).getRepositoryService();
        Model model = repositoryService.getModel(modelId);
        if (model == null) {
            return null;
        }
        
        List<Model> models = repositoryService.createModelQuery().modelKey(model.getKey()).list();
        for (Model updateModel : models) {
            if (!updateModel.getId().equals(modelId)) {
                updateModel.setKey(modelKey);
                repositoryService.saveModel(updateModel);
            }
        }

        return null;
    }

}