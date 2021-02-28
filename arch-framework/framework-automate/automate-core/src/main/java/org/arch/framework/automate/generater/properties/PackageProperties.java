package org.arch.framework.automate.generater.properties;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.arch.framework.automate.generater.core.SchemaProperties;

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
@ToString
public class PackageProperties implements SchemaProperties {
    // 包类型（entity,dao,service.....）
    private String type;
    // 自定义子包，如果没有集成basePack
    private String pkg;
    // 当前包的模板
    private String template;
    // 后缀
    private String suffix;
    // 启动类
    private String bootstrap;
    // 文件扩展名，默认为java ,用户可以自定义
    private String ext = ".java";

}
