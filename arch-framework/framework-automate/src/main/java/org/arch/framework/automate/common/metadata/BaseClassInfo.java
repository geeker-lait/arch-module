package org.arch.framework.automate.common.metadata;

import lombok.Data;

/**
 * 类描述信息
 */
@Data
public abstract class BaseClassInfo {

    /**
     * 类名，如：User
     */
    protected String className;

    /**
     * 包名,如：java.lang.String
     */
    protected String packageName;

    /**
     * 模块名
     */
    protected String moduleName;

}
