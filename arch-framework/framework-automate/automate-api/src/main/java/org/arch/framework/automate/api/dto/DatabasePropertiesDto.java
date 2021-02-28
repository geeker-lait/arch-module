package org.arch.framework.automate.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * copy form {@link org.arch.framework.automate.generater.properties.DatabaseProperties}
 * @author junboXiang
 * @version V1.0
 * 2021-02-28
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class DatabasePropertiesDto {
    private String driver;
    private String dialect;
    private String host;
    private Integer port;
    private String name;
    private String username;
    private String password;
}
