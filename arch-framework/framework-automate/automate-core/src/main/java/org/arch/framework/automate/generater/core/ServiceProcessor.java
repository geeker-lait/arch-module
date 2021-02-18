package org.arch.framework.automate.generater.core;

import org.arch.framework.automate.generater.config.DataProperties;
import org.arch.framework.automate.generater.config.GeneratorConfig;
import org.arch.framework.automate.generater.render.RenderingRequest;
import org.springframework.stereotype.Service;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date 12/20/2020 9:57 AM
 */
@Service
public class ServiceProcessor implements TemplateProcessor {
    @Override
    public void build(GeneratorConfig generatorConfig) {

    }

    @Override
    public TemplateName getTemplate() {
        return null;
    }

    @Override
    public void process(String code, RenderingRequest renderingRequest) {

    }

    @Override
    public void createModule(RenderingRequest renderingRequest) {

    }

    @Override
    public void createFile(String code, RenderingRequest renderingRequest) {

    }

    @Override
    public DataProperties buildData() {
        return null;
    }
}
