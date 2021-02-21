package org.arch.framework.automate.generater.config.properties;

import lombok.Data;
import org.arch.framework.automate.generater.config.DataProperties;

@Data
public class DependencieProterties implements DataProperties {
    private String groupId;
    private String artifactId;
    private String version;
}
