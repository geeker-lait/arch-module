package org.arch.framework.automate.generater.core;

import org.arch.framework.automate.generater.render.RenderingRequest;

/**
 * @author lait.zhang@gmail.com
 * @description: 模板文件处理
 * @weixin PN15855012581
 * @date 12/18/2020 8:02 PM
 */
public interface TemplateProcessor<T extends RenderingRequest> {

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
