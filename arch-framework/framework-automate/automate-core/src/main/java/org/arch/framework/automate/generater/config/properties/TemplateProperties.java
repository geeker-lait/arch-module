package org.arch.framework.automate.generater.config.properties;

import cn.hutool.extra.template.TemplateEngine;
import lombok.Data;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date :
 */
@Data
public class TemplateProperties {
    private String dir;
    private String resourceMode;
    // 模板引擎
    private TemplateEngine templateEngine;

}
