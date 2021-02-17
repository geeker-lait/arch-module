package org.arch.framework.automate.generater.config;

import lombok.Data;

/**
 * @author lait.zhang@gmail.com
 * @description:
 * driver: com.mysql.cj.jdbc.Driver
 * url: "jdbc:mysql://localhost:3306/uni?characterEncoding=UTF-8&serverTimezone=Asia/Shanghai"
 * username: root
 * password: root
 * @weixin PN15855012581
 * @date :
 */
@Data
public class Database {
    private String driver;
    private String url;
    private String username;
    private String password;
}
