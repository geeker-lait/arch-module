package org.arch.framework.generater.config;

import lombok.Data;

/**
 * 模块名称
 */
@Data
public class ModuleConfig {

    /**
     * 包名
     */
    private String packageName;

    /**
     * 类名后缀，如：Service，则类名后缀为：xxxService
     */
    private String classNameSuffix;

    /**
     * 对应的模板文件名称
     */
    private String ftlName;

    /**
     * 保存的文件路径
     */
    private String savePath;
}
