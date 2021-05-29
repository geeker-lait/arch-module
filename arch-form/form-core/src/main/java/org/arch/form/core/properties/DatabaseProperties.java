package org.arch.form.core.properties;

import lombok.Data;

@Data
public class DatabaseProperties {
    private String database;
    private String driver;
    private String url;
    private String port;
    private String username;
    private String password;
}
