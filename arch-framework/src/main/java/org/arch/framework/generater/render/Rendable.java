package org.arch.framework.generater.render;

import org.arch.framework.generater.metadata.EntityInfo;

/**
 * 模板渲染接口
 */
public interface Rendable {

    /**
     * 添加斜杠
     */
    String SLASH = "/";

    /**
     * 渲染为java文件
     *
     * @param entityInfo 实体信息
     * @param module     模块名称
     * @param savePath   保存的目录
     * @return 渲染结果
     */
    RenderingResponse render(EntityInfo entityInfo, String module, String savePath);

}
