package org.arch.framework.ums.enums;

/**
 * 推荐类型
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.1.15 10:09
 */
public enum SourceType {
    /**
     * 用户推荐, 003_3: 00 为 SourceType 业务前缀, 3_3 为 rsId(rs=Relationship)_memberLevelId
     */
    USER("00", "_"),
    /**
     *  百度
     */
    BAIDU("01", "_"),
    /**
     *  百度小程序
     */
    BAIDU_MINI("02", "_"),
    /**
     * 抖音
     */
    DOUYIN("03", "_"),
    /**
     * 抖音小程序
     */
    DOUYIN_MINI("04", "_"),
    /**
     * 头条
     */
    TOUTIAO("05", "_"),
    /**
     * 头条小程序
     */
    TOUTIAO_MINI("06", "_"),
    /**
     * 微信
     */
    WEIXIN("07", "_"),
    /**
     * 微信公众号
     */
    WEIXIN_MP("08", "_"),
    /**
     * 微信小程序
     */
    WINXIN_MINI("09", "_"),
    /**
     * 微博
     */
    WEIBO("10", "_"),
    /**
     * 微博小程序
     */
    WEIBO_MINI("11", "_"),
    /**
     * 快手
     */
    KUAISHOU("12", "_"),
    /**
     * 快手小程序
     */
    KUAISHOU_MINI("13", "_"),
    /**
     * 财智有道
     */
    ARCH("18", "_"),
    /**
     * 其他
     */
    OTHER("20", "_");


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

    SourceType(String prefix, String delimiter) {
        this.prefix = prefix;
        this.length = prefix.length();
        this.delimiter = delimiter;
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
}
