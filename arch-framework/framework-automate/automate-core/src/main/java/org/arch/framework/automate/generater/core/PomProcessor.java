package org.arch.framework.automate.generater.core;

import lombok.extern.slf4j.Slf4j;
import org.arch.framework.automate.generater.ex.CodegenException;
import org.arch.framework.automate.generater.render.RenderingRequest;

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
public class PomProcessor extends AbstractProcessor implements TemplateProcessor {
    @Override
    void createModule(RenderingRequest renderingRequest) {

        log.info("==========={}", renderingRequest.getSavePath());
        log.info("==========={}", renderingRequest.getModuleName());
        creatMavenDirectory(renderingRequest);

    }

    @Override
    void createFile(String code, RenderingRequest renderingRequest) {
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
    public void process(String code, RenderingRequest renderingRequest) {
        createModule(renderingRequest);
        createFile(code, renderingRequest);
    }
}
