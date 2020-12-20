package org.arch.framework.generater.core;

import org.arch.framework.generater.metadata.ModuleInfo;
import org.arch.framework.generater.render.RenderingRequest;

/**
 * @author lait.zhang@gmail.com
 * @description: 模板文件处理
 * @weixin PN15855012581
 * @date 12/18/2020 8:02 PM
 */
public interface FtlProcessor{

    /**
     * 获取文件
     * @return
     */
    String getFile();




    void process(String code, RenderingRequest renderingRequest);
}
