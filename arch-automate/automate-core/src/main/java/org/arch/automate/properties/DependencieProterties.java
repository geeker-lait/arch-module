package org.arch.automate.properties;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.arch.automate.core.ConfigProperties;

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
