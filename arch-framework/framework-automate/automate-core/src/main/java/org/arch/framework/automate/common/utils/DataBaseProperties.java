package org.arch.framework.automate.common.utils;

import lombok.Data;

/**
 * 数据库的连接配置类
 */
@Data
public class DataBaseProperties {

    /**
     * 数据库连接，如：jdbc:mysql://localhost:3306/test?characterEncoding=UTF-8&serverTimezone=Asia/Shanghai
     */
    private String jdbcUrl;

    /**
     * 数据库的用户名，如：root
     */
    private String user;

    /**
     * 数据库的密码,如：123456
     */
    private String password;

    /**
     * 数据库的驱动，如：com.mysql.cj.jdbc.Driver
     */
    private String driver;
}
