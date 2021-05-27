package org.arch.framework.init.service;

/**
 * 数据字典初始化接口
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.5.27 15:17
 */
public interface DictionaryInitService {
    /**
     * 数据字典初始化, 项目初始时使用.
     * @return  是否成功初始化
     */
    boolean init();
}
