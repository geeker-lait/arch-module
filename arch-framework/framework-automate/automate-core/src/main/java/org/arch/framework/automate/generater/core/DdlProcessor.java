package org.arch.framework.automate.generater.core;

import org.arch.framework.automate.generater.config.DataProperties;
import org.arch.framework.automate.generater.config.GeneratorConfig;
import org.arch.framework.automate.generater.ex.CodegenException;
import org.arch.framework.automate.generater.render.RenderingRequest;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date 12/20/2020 9:57 AM
 */
@Service
public class DdlProcessor extends AbstractProcessor implements TemplateProcessor {
    @Override
    public void build(GeneratorConfig generatorConfig) {

    }

    @Override
    public TemplateName getTemplate() {
        return TemplateName.DDL;
    }

    @Override
    public DataProperties buildData() {
        return null;
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
