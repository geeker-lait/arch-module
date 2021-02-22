package org.arch.framework.automate.generater.config.properties;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.arch.framework.automate.generater.config.DataProperties;

@Data
@NoArgsConstructor
public class DependencieProterties implements DataProperties {
    private String groupId;
    private String artifactId;
    private String version;
}
