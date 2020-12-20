package org.arch.framework.generater.render;

import lombok.Data;

import java.io.Serializable;

/**
 * 渲染结果
 */
@Data
public class RenderingResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 渲染是否成功：true为成功
     */
    private boolean success;

    /**
     * 渲染错误信息
     */
    private String errorMsg;

    /**
     * 模板名称
     */
    private String ftlName;

    /**
     * 包名
     */
    private String packageName;

    /**
     * 类名
     */
    private String className;
}
