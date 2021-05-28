package org.arch.automate.common;

/**
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.5.27 22:38
 */
public interface Pkg {
    /**
     * 包名称
     * @return 包名称
     */
    String getPkg();

    /**
     * 设置包名称
     * @param pkg 包名称
     * @return {@link Pkg}
     */
    Pkg setPkg(String pkg);

}
