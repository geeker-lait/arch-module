package org.arch.automate.properties;

import cn.hutool.extra.template.TemplateEngine;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.arch.automate.core.ConfigProperties;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date :
 */
@Data
@NoArgsConstructor
@ToString
public class TemplateProperties implements ConfigProperties {
    private String dir;
    // 默认从classpath加载
    private String resourceMode = "classpath";
    // 模板引擎
    private TemplateEngine templateEngine;
}
