package org.arch.framework.ums.enums;

/**
 * 推荐类型
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.1.15 10:09
 */
public enum SourceType {
    /**
     *  百度
     */
    BAIDU("baidu_", "_"),
    /**
     *  百度小程序
     */
    BAIDU_MINI("baiduMini_", "_"),
    /**
     * 抖音
     */
    DOUYIN("douyin_", "_"),
    /**
     * 抖音小程序
     */
    DOUYIN_MINI("douyinMini_", "_"),
    /**
     * 头条
     */
    TOUTIAO("toutiao_", "_"),
    /**
     * 头条小程序
     */
    TOUTIAO_MINI("toutiaoMini_", "_"),
    /**
     * 微信
     */
    WEIXIN("weixin_", "_"),
    /**
     * 微信公众号
     */
    WEIXIN_MP("weixinMp_", "_"),
    /**
     * 微信小程序
     */
    WINXIN_MINI("winxinMini_", "_"),
    /**
     * 微博
     */
    WEIBO("weibo_", "_"),
    /**
     * 微博小程序
     */
    WEIBO_MINI("weiboMini_", "_"),
    /**
     * 快手
     */
    KUAISHOU("kuaishou_", "_"),
    /**
     * 快手小程序
     */
    KUAISHOU_MINI("kuaishouMini_", "_"),
    /**
     * 用户推荐, 存储使用 user_accountId_relationshipId_rsOrg_rsDeep_rsSeq,
     */
    USER("user_", "_"),
    /**
     * 财智有道
     */
    ARCH("arch_", "_"),
    /**
     * 其他
     */
    OTHER("other_", "_");


    /**
     * 分隔符
     */
    private final String delimiter;

    /**
     * 推广或用户推荐业务前缀
     */
    private final String prefix;

    SourceType(String prefix, String delimiter) {
        this.prefix = prefix;
        this.delimiter = delimiter;
    }

    public String getDelimiter() {
        return delimiter;
    }

    public String getPrefix() {
        return prefix;
    }
}
