package org.arch.framework.automate.generater.core;

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
public class DdlProcessor extends AbstractProcessor implements TemplateProcessor {
    @Override
    public TemplateName getTemplate() {
        return TemplateName.DDL;
    }

    @Override
    public void process(String code, RenderingRequest renderingRequest) {
        createFile(code, renderingRequest);
    }


    @Override
    public void createModule(RenderingRequest renderingRequest) {

    }


    @Override
    public void createFile(String code, RenderingRequest renderingRequest) {
        renderingRequest.getDatabaseInfos().forEach(databaseInfo -> {
            try {
                saveToFile(code, renderingRequest.getSavePath() + File.separator + databaseInfo.getModuleName() + File.separator + MAIN_RESOURCES, databaseInfo.getModuleName() + "-schema.sql", renderingRequest.isCover());
            } catch (IOException e) {
                e.printStackTrace();
                throw new CodegenException(String.format("render %s code source failed.", renderingRequest.getEntity().getClassName()), e);
            }
        });
    }
}
