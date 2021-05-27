package org.arch.framework.ums.enums;

/**
 * 银行卡类型
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.1.29 20:27
 */
public enum CardType implements DictionaryItemInfo {

    /**
     * 借记卡
     */
    DEBIT_CARD("借记卡", ""),
    /**
     * 信用卡
     */
    CREDIT_CARD("信用卡", "");

    /**
     * title
     */
    private final String title;
    /**
     * 备注
     */
    private final String mark;

    CardType(String title, String mark) {
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
