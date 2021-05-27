package org.arch.framework.ums.enums;

/**
 * 推荐类型
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.1.15 10:09
 */
public enum SourceType implements DictionaryItemInfo {
    /**
     * 用户推荐, 005L3: 00 为 SourceType 业务前缀, 5L3 为 5=rsId(rs=Relationship), 3=memberLevelId
     */
    USER("00", "L", "用户推荐, 005L3: 00 为 SourceType 业务前缀, 5L3 为 5=rsId(rs=Relationship), 3=memberLevelId", ""),
    /**
     *  百度
     */
    BAIDU("01", "_", "百度", ""),
    /**
     *  百度小程序
     */
    BAIDU_MINI("02", "_", "百度小程序", ""),
    /**
     * 抖音
     */
    DOUYIN("03", "_", "抖音", ""),
    /**
     * 抖音小程序
     */
    DOUYIN_MINI("04", "_", "抖音小程序", ""),
    /**
     * 头条
     */
    TOUTIAO("05", "_", "头条", ""),
    /**
     * 头条小程序
     */
    TOUTIAO_MINI("06", "_", "TOUTIAO_MINI", ""),
    /**
     * 微信
     */
    WEIXIN("07", "_", "微信", ""),
    /**
     * 微信公众号
     */
    WEIXIN_MP("08", "_", "微信公众号", ""),
    /**
     * 微信小程序
     */
    WINXIN_MINI("09", "_", "微信小程序", ""),
    /**
     * 微博
     */
    WEIBO("10", "_", "微博", ""),
    /**
     * 微博小程序
     */
    WEIBO_MINI("11", "_", "微博小程序", ""),
    /**
     * 快手
     */
    KUAISHOU("12", "_", "快手", ""),
    /**
     * 快手小程序
     */
    KUAISHOU_MINI("13", "_", "快手小程序", ""),
    /**
     * 财智有道
     */
    ARCH("18", "_", "财智有道", ""),
    /**
     * 其他
     */
    OTHER("20", "_", "其他", "");


    /**
     * 分隔符
     */
    private final String delimiter;

    /**
     * 前缀字符串长度
     */
    private final Integer length;

    /**
     * 推广或用户推荐业务前缀
     */
    private final String prefix;

    /**
     * title
     */
    private final String title;
    /**
     * 备注
     */
    private final String mark;

    SourceType(String prefix, String delimiter, String title, String mark) {
        this.prefix = prefix;
        this.length = prefix.length();
        this.delimiter = delimiter;
        this.title = title;
        this.mark = mark;
    }

    public String getDelimiter() {
        return delimiter;
    }

    public String getPrefix() {
        return prefix;
    }

    public Integer getLength() {
        return length;
    }

    @Override
    public String getTitle() {
        return title;
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
        return mark;
    }
}
