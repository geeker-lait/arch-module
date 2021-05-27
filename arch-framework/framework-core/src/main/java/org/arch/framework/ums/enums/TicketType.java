package org.arch.framework.ums.enums;

/**
 * 优惠券类型
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.1.29 20:13
 */
public enum TicketType implements DictionaryItemInfo {
    /**
     * 打折
     */
    DISCOUNT("打折", ""),
    /**
     * 优惠
     */
    COUPON("优惠", ""),
    /**
     * 抵用
     */
    VOUCHER("抵用", "");

    /**
     * title
     */
    private final String title;
    /**
     * 备注
     */
    private final String mark;

    TicketType(String title, String mark) {
        this.title = title;
        this.mark = mark;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public String getVal() {
        return this.name().toLowerCase();
    }

    @Override
    public int getSeq() {
        return this.ordinal();
    }

    @Override
    public String getMark() {
        return this.mark;
    }
}
