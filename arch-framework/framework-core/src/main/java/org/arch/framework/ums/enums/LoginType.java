
package org.arch.framework.ums.enums;

/**
 * 账号登录类型
 * @author YongWu zheng
 * @version V2.0  Created by 2020.12.28 11:39
 */
public enum LoginType {
    /**
     * 本系统用户, 账号密码方式登录类型
     */
    ACCOUNT,
    /**
     * email 账号登录类型
     */
    EMAIL,
    /**
     * 手机验证码或运营商登录类型
     */
    PHONE,
    /**
     * 第三方授权登录类型
     */
    OAUTH2

}
