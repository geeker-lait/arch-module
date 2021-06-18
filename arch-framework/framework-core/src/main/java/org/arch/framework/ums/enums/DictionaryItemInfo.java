package org.arch.framework.ums.enums;

/**
 * 数据字典明细信息接口
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.5.25 21:08
 */
public interface DictionaryItemInfo {

    /**
     * 标题
     * @return title
     */
    String getTitle();

    /**
     * 标题对应的值
     * @return val
     */
    String getVal();

    /**
     * title 顺序
     * @return seq
     */
    int getSeq();

    /**
     * 备注
     * @return mark
     */
    String getMark();
}
