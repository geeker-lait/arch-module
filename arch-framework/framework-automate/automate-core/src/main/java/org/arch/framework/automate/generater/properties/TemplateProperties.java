package org.arch.framework.automate.generater.properties;

import cn.hutool.extra.template.TemplateEngine;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date :
 */
@Data
@NoArgsConstructor
public class TemplateProperties {
    private String dir;
    private String resourceMode;
    // 模板引擎
    private TemplateEngine templateEngine;

}
