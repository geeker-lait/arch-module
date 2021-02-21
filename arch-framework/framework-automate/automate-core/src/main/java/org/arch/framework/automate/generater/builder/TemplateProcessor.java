package org.arch.framework.automate.generater.builder;

import org.arch.framework.automate.generater.config.GeneratorConfig;
import org.arch.framework.automate.generater.core.TemplateName;
import org.arch.framework.automate.generater.render.RenderingRequest;

import java.io.IOException;

/**
 * @author lait.zhang@gmail.com
 * @description: 模板文件处理
 * @weixin PN15855012581
 * @date 12/18/2020 8:02 PM
 */
public interface TemplateProcessor<T extends RenderingRequest> {


    /**
     * 根据generatorConfig 构建项目
     *
     * @param generatorConfig
     * @throws IOException
     */
    void build(GeneratorConfig generatorConfig) throws IOException;

    /**
     * 获取文件
     *
     * @return
     */
    TemplateName getTemplate();


    /**
     * 模板处理器
     *
     * @param code
     * @param renderingRequest
     */
    void process(String code, T renderingRequest);

}
