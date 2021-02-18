package org.arch.framework.automate.generater.core;

import lombok.extern.slf4j.Slf4j;
import org.arch.framework.automate.generater.config.DataProperties;
import org.arch.framework.automate.generater.config.GeneratorConfig;
import org.arch.framework.automate.generater.ex.CodegenException;
import org.arch.framework.automate.generater.render.RenderingRequest;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date :
 */
@Slf4j
@Service
public class PomProcessor extends AbstractProcessor implements TemplateProcessor {
    @Override
    public void build(GeneratorConfig generatorConfig) {

    }

    @Override
    public void createModule(RenderingRequest renderingRequest) {

        log.info("==========={}", renderingRequest.getSavePath());
        log.info("==========={}", renderingRequest.getModuleName());
        creatMavenDirectory(renderingRequest);

    }

    @Override
    public void createFile(String code, RenderingRequest renderingRequest) {
        try {
            saveToFile(code, renderingRequest.getSavePath() + renderingRequest.getModuleName(), "pom.xml", renderingRequest.isCover());
        } catch (IOException e) {
            e.printStackTrace();
            throw new CodegenException(String.format("render %s code source failed.", renderingRequest.getEntity().getClassName()), e);
        }
    }

    @Override
    public TemplateName getTemplate() {
        return TemplateName.POM;
    }

    @Override
    public DataProperties buildData() {
        return null;
    }

    @Override
    public void process(String code, RenderingRequest renderingRequest) {
        createModule(renderingRequest);
        createFile(code, renderingRequest);
    }
}
