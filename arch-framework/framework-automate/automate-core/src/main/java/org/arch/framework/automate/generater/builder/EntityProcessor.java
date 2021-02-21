package org.arch.framework.automate.generater.builder;

import org.arch.framework.automate.generater.config.GeneratorConfig;
import org.arch.framework.automate.generater.core.TemplateName;
import org.arch.framework.automate.generater.ex.CodegenException;
import org.arch.framework.automate.generater.render.RenderingRequest;

import java.io.File;
import java.io.IOException;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date 12/20/2020 9:57 AM
 */
//@Service
public class EntityProcessor extends AbstractProcessor implements TemplateProcessor {
    @Override
    public void build(GeneratorConfig generatorConfig) {

    }

    @Override
    public void createModule(RenderingRequest renderingRequest) {
        creatMavenDirectory(renderingRequest);
    }

    @Override
    public void createFile(String code, RenderingRequest renderingRequest) {
        String pack = renderingRequest.getPackageName().replace(".", File.separator);
        renderingRequest.getDatabaseInfos().forEach(databaseInfo -> {
            String mn = databaseInfo.getModuleName();
            databaseInfo.getEntityInfos().forEach(entityInfo -> {
                try {
                    saveToFile(code, renderingRequest.getSavePath() + File.separator + mn + File.separator + MAIN_JAVA + pack + File.separator + mn + File.separator + "entity", entityInfo.getTableName() + ".java", renderingRequest.isCover());
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new CodegenException(String.format("render %s code source failed.", renderingRequest.getEntity().getClassName()), e);
                }
            });
        });

    }



    @Override
    public TemplateName getTemplate() {
        return TemplateName.ENTITY;
    }


    @Override
    public void process(String code, RenderingRequest renderingRequest) {
        createModule(renderingRequest);
        createFile(code, renderingRequest);
    }
}
