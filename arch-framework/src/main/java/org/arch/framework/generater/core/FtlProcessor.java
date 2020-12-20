package org.arch.framework.generater.core;

import org.arch.framework.generater.render.RenderingRequest;

/**
 * @author lait.zhang@gmail.com
 * @description: 模板文件处理
 * @weixin PN15855012581
 * @date 12/18/2020 8:02 PM
 */
public interface FtlProcessor {

    /**
     * 获取文件
     * @return
     */
    String getFile();

    /**
     * 处理
     * @param code
     * @param renderingRequest
     */
    void process(String code, RenderingRequest renderingRequest);
}
