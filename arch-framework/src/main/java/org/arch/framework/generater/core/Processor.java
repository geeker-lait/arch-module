package org.arch.framework.generater.core;

import org.arch.framework.generater.render.RenderingRequest;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date :
 */
public interface Processor {

    /**
     * 处理
     * @param code
     * @param renderingRequest
     */
    void process(String code, RenderingRequest renderingRequest);


}
