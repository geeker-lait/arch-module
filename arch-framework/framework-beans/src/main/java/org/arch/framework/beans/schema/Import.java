package org.arch.framework.beans.schema;

import java.util.Set;

/**
 * 获取需要导入的 import 集合 接口
 *
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.5.1 15:41
 */
public interface Import {
    /**
     * 获取需要导入的 import 集合
     *
     * @return import 集合
     */
    Set<String> getImports();
}
