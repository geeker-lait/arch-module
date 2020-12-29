package org.arch.ums.account.dto;

import lombok.Data;
import org.arch.ums.account.enums.ChannelType;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * web 端 用户注册请求信息
 * @author YongWu zheng
 * @version V2.0  Created by 2020.12.28 11:39
 */
@Data
public class AuthRegWebDto implements Serializable {

    private static final long serialVersionUID = 769725136116780083L;

    private Long id;

    /**
     * 账户名,邮箱,手机号,第三方用户ID, 对应账号-标识 identifier
     */
    @NotNull(message = "账户名不能为空")
    private String username;
    /**
     * 站内账号是密码, 第三方是 accessToken, 手机登录是空字符串或指定字符串. 对应账号-标识 credential<br>
     * 注意: {@link #channelType} 为 {@link ChannelType#PHONE} 时有后端赋值 "空字符串或指定字符串"
     */
    @NotNull(message = "密码不能为空")
    private String password;
    /**
     * 登录类型: 站内用户(ACCOUNT), 邮箱(EMAIL), 手机(PHONE), 第三方(OAUTH),
     * 由后端赋值
     */
    private ChannelType channelType;
    /**
     * 头像
     */
    private String avatar;

    /**
     * 用户昵称, 第三方登录用第三方的用户名, 手机号登录与用户密码登录随机生成
     */
    private String nickName;

    /**
     * 用户来源
     */
    private String source;

}