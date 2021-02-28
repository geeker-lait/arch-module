package org.arch.framework.automate.generater.properties;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.arch.framework.automate.generater.core.SchemaProperties;

@Data
@NoArgsConstructor
@ToString
public class DependencieProterties implements SchemaProperties {
    private String groupId;
    private String artifactId;
    private String version;
    private String type;
    private String scope;
}
