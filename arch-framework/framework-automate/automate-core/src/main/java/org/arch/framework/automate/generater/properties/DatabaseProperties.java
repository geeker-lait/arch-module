package org.arch.framework.automate.generater.properties;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.util.List;

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
@NoArgsConstructor
public class DatabaseProperties {
    private String driver;
    private String dialect;
    private String host;
    private Integer port;
    private String name;
    private String username;
    private String password;
    @NestedConfigurationProperty
    private List<TableProperties> tables;
}
