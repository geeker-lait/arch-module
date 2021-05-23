package org.arch.framework.automate.common.configuration;

import lombok.Data;
import org.arch.framework.automate.generater.core.SchemaConfiguration;

@Data
public class DatabaseConfiguration implements SchemaConfiguration {
    private String database;
    private String driver;
    private String url;
    private String port;
    private String username;
    private String password;
}
