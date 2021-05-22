package org.arch.framework.automate.generater.properties;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.arch.framework.automate.generater.core.ConfigProperties;

@Data
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class DependencieProterties implements ConfigProperties {
    private String groupId;
    private String artifactId;
    private String version;
    private String type;
    private String scope;
    private boolean isModule;
}
