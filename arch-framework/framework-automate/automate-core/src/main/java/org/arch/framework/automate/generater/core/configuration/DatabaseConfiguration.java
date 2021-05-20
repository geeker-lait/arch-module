package org.arch.framework.automate.generater.core.configuration;

import lombok.Data;
import org.arch.framework.automate.generater.core.SchemaConfiguration;

@Data
public class DatabaseConfiguration implements SchemaConfiguration {
    private String driver;
    private String url;
    private String username;
    private String password;
}
