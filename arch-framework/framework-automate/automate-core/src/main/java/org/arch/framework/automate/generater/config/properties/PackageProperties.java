package org.arch.framework.automate.generater.config.properties;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lait.zhang@gmail.com
 * @description:
 * id: entity
 * pkg:
 * template:
 * suffix:
 * @weixin PN15855012581
 * @date 2/17/2021 8:22 PM
 */
@Data
@NoArgsConstructor
public class PackageProperties {
    private String type;
    private String pkg;
    private String template;
    private String suffix;
    private String ext = ".java";

}
