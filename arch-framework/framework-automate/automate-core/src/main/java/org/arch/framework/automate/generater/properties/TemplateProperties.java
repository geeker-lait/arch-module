package org.arch.framework.automate.generater.properties;

import cn.hutool.extra.template.TemplateEngine;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date :
 */
@Data
@NoArgsConstructor
@ToString
public class TemplateProperties {
    private String dir;
    // 默认从classpath加载
    private String resourceMode = "classpath";
    // 模板引擎
    private TemplateEngine templateEngine;
}
