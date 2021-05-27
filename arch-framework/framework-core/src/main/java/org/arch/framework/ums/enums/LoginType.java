
package org.arch.framework.ums.enums;

/**
 * 账号登录类型
 * @author YongWu zheng
 * @version V2.0  Created by 2020.12.28 11:39
 */
public enum LoginType implements DictionaryItemInfo {
    /**
     * 用户名密码方式登录类型
     */
    USERNAME("用户名密码方式登录类型", ""),
    /**
     * email 账号登录类型
     */
    EMAIL("email 账号登录类型", ""),
    /**
     * 手机验证码或运营商登录类型
     */
    PHONE("手机验证码或运营商登录类型", ""),
    /**
     * 第三方授权登录类型
     */
    OAUTH2("第三方授权登录类型", "");

    /**
     * title
     */
    private final String title;
    /**
     * 备注
     */
    private final String mark;

    LoginType(String title, String mark) {
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
