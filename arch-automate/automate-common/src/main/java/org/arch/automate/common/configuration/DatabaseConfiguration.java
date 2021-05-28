package org.arch.automate.common.configuration;

import lombok.Data;

@Data
public class DatabaseConfiguration implements SchemaConfiguration {
    private String database;
    private String driver;
    private String url;
    private String port;
    private String username;
    private String password;
}
