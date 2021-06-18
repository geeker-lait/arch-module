package org.arch.framework.ums.enums;

/**
 * 电话类型
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.1.29 20:33
 */
public enum Mno {
    /**
     * 移动
     */
    CMCC("中国移动"),
    /**
     * 联通
     */
    CUCC("中国联通"),
    /**
     * 电信
     */
    CTCC("中国电信"),
    /**
     * 广电
     */
    CBNI("中国广电"),
    /**
     * 座机
     */
    LANDLINE("座机");

    /**
     * 描述
     */
    private final String desc;

    Mno(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
